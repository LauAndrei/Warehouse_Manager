package dataAccessLayer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.*;
import java.util.List;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected final static Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    public static TableModel createTableModel(List<?> list) throws IllegalAccessException {
        int tableSize = list.get(0).getClass().getDeclaredFields().length;
        String[] columnNames = new String[tableSize];
        int columnNumber = 0;
        for (Field currentField : list.get(0).getClass().getDeclaredFields()) {
            currentField.setAccessible(true);
            columnNames[columnNumber] = currentField.getName();
            columnNumber++;
        }
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        for (Object o : list) {
            Object[] objects = new Object[tableSize];
            int col = 0;
            for (Field currentField : o.getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                objects[col] = currentField.get(o);
                col++;
            }
            model.addRow(objects);
        }
        return model;
    }

}
