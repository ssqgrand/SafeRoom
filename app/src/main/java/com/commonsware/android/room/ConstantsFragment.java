/***
 Copyright (c) 2008-2017 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 Covered in detail in the book _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.commonsware.android.room;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ConstantsFragment extends ListFragment implements DialogInterface.OnClickListener {
    private AsyncTask task = null;
    private ArrayAdapter<Constant> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (adapter == null) {
            adapter = new ConstantsAdapter(getActivity());
            setListAdapter(adapter);
            task = new LoadCursorTask(getActivity()).execute();
        }
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.cancel(false);
        }

        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            add();
            return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void add() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.add_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.add_title).setView(addView)
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancel, null).show();
    }

    public void onClick(DialogInterface di, int whichButton) {
        AlertDialog dlg = (AlertDialog) di;
        EditText title = (EditText) dlg.findViewById(R.id.title);
        EditText value = (EditText) dlg.findViewById(R.id.value);

        task = new InsertTask(getActivity()).execute(title.getText().toString(), value.getText().toString());
    }

    abstract private class BaseTask<T> extends AsyncTask<T, Void, List<Constant>> {
        Context app;

        BaseTask(Context ctxt) {
            app = ctxt.getApplicationContext();
        }

        @Override
        public void onPostExecute(List<Constant> result) {
            adapter.clear();
            adapter.addAll(result);
            task = null;
        }

        List<Constant> doQuery() {
            return (ConstantsDatabase.get(app).constantsDao().allConstants());
        }
    }

    private class LoadCursorTask extends BaseTask<Void> {
        private LoadCursorTask(Context ctxt) {
            super(ctxt);
        }

        @Override
        protected List<Constant> doInBackground(Void... params) {
            return (doQuery());
        }
    }

    private class InsertTask extends BaseTask<String> {
        private InsertTask(Context ctxt) {
            super(ctxt);
        }

        @Override
        protected List<Constant> doInBackground(String... values) {
            Constant constant = new Constant();

            constant.title = values[0];
            constant.value = Double.parseDouble(values[1]);
            ConstantsDatabase.get(app).constantsDao().insertConstant(constant);

            return (doQuery());
        }
    }

    private class ConstantsAdapter extends ArrayAdapter<Constant> {
        ConstantsAdapter(@NonNull Context context) {
            super(context, R.layout.row, R.id.title);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            TextView value = (TextView) row.findViewById(R.id.value);

            value.setText(String.format("%f", getItem(position).value));

            return (row);
        }
    }
}
