/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.tableModel;

import hr.algebra.model.Actor;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wExzEk
 */
public class ActorTableModel extends AbstractTableModel{
    private static final String[] COLUMNS
            = {
                "IDActor",
                "Name"
            };
    private List<Actor> actors;

    public ActorTableModel(List<Actor> actors) {
        this.actors = actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return actors.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column]; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return actors.get(rowIndex).getId();
            case 1:
                return actors.get(rowIndex).getFullName();
            
            default:
                throw new AssertionError();
        }
    }
}
