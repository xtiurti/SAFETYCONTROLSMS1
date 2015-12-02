package br.com.twautomacao.safetycontrolsms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ComandosConteudo {

        /**
         * An array of sample (dummy) items.
         */
        public static ArrayList<DummyItem> ITEMS = new ArrayList<DummyItem>();

        /**
         * A map of sample (dummy) items, by ID.
         */
        public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

        static {
                // Add 3 sample items.
                addItem(new DummyItem("1", "Red Circle", R.drawable.logo));
                addItem(new DummyItem("2", "Orange Circle", R.drawable.logo));
                addItem(new DummyItem("3", "Green Circle", R.drawable.logo));
                addItem(new DummyItem("4", "Colorful Circle", R.drawable.logo));
        }

        private static void addItem(DummyItem item) {
                ITEMS.add(item);
                ITEM_MAP.put(item.id, item);
        }

        /**
         * A dummy item representing a piece of content.
         */
        public static class DummyItem {
                public String id;
                public String content;
                public int resourceId;

                public DummyItem(String id, String content, int resourceId) {
                        this.id = id;
                        this.content = content;
                        this.resourceId = resourceId;
                }

                @Override
                public String toString() {
                        return content;
                }
        }
}

