package com.example.opilane.menukuningas;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Need kaks konstanti defineerivad menüü itemid. Peavad olema unikaalsed integerid.
    public static final int MENU_NAME = Menu.FIRST + 1;
    public static final int MENU_TEXT = Menu.FIRST + 2;

    private List<King> kings = (new Kings()).getKings();
    private List<String> lines = new ArrayList();
    private ListView view, view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.listKings1);
        from = findViewById(R.id.txtFrom);
        to = findViewById(R.id.txtTo);

        disable(from);
        disable(to);

        view2.setAdapter(new ArrayAdapter<King>(this, android.R.layout.simple_list_item_1, kings));
        view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                update (kings.get(position));
            }
        });
        // Registeerid listview komponendi menuga
        registerForContextMenu(view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(menu.NONE, MENU_NAME, Menu.NONE, "King");
        menu.add(Menu.NONE, MENU_NAME, Menu.NONE, "Description");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case MENU_NAME:
                Toast.makeText(this, King.getKing(), Toast.LENGTH_LONG).show();
            case MENU_TEXT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(King.getName);
                builder.setMessage(King.getText);
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    public void update(King king){
        String line = toString(king);
        if (!lines.remove(line)) lines.add(line);
        view.setAdapter(new ArrayAdapter<String>()); // siin pooleli
    }

    private String toString(King king) {
        if (king.getFrom() != 0 && king.getTo() != 9999)
            return String.format("%s: %d - %d", king.getName(), king.getFrom(), king.getTo());
        if (king.getFrom() != 0)
            return String.format("%s: %d -", king.getName(), king.getFrom());
        if (king.getTo() != 9999)
            return String.format("%s: - %d", king.getName(), king.getTo());
        return king.getName();
    }
}
