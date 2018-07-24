package com.carrot.carrotshop;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Scanner;

public class Lang {
	public static String HELP_DESC_CMD_REPORT = "Generare a CarrotShop report";
	public static String HELP_DESC_CMD_SREPORT = "Generare a CarrotShop report for iSigns";
	public static String HELP_DESC_CMD_OREPORT = "Generate a CarrotShop report for another player";
	public static String HELP_DESC_CMD_SPAM = "Toggle shop message when someone use your shop";
	public static String HELP_DESC_CMD_WIKI = "Displays a link to the shop wiki";
	public static String HELP_DESC_CMD_CONFIG_CURRENCY = "Get/Set the default currency";
	public static String HELP_DESC_CMD_CONFIG_RELOAD = "Reload the language file";
	public static String HELP_DESC_CMD_CONFIG = "Change or reload config of the plugin";
	public static String HELP_DESC_CMD_MAIN = "Main CarrotShop command";

	public static String HELP_HEADER_CMD_MAIN = "/carrotshop";
	public static String HELP_HEADER_CMD_CONFIG = "/carrotshop config";

	public static String STATUS_ON = "ON";
	public static String STATUS_OFF = "OFF";

	public static String LOCATION_STORED = "Stored location of item";
	public static String LOCATION_REMOVED = "Removed location of item";

	public static String REPORT_PREPARE = "The report is being prepared...";
	public static String REPORT_READY = "Report is ready: %url%";
	public static String REPORT_ERROR_DATA = "No data found to generate report";
	public static String REPORT_ERROR_PERM = "You do not have permission to generate reports";
	public static String REPORT_ERROR_OPERM = "You do not have permission to generate reports for other players";

	public static String CURRENCY_VALUE = "Shop currency set to %name%";
	public static String CURRENCY_SERVER = "Shop currency set to match server's config";
	public static String SHOP_CURRENCY = "This sign will use default currency: %name%";
	public static String SHOP_CURRENCY_LOOP = "Left click the sign with a stick to use another currency";

	public static String PRICE_ZERO = "nothing";
	public static String PRICE_ONE = "%value% %currencyFull%";
	public static String PRICE_DEFAULT = "%value% %currencyFullPlural%";
	public static String PRICE_TRANSLATION_HINTS = "Accepted placeholders for PRICE_*: %value% %currencyFull% %currencyFullPlural% %currencySymbol%";

	public static String SHOP_USED = "Someone used your shop signs while you were away. Use %cmd% for more details";
	public static String SHOP_OVERRIDE = "This shop would override a shop you do not own. Abort.";

	public static String WIKI_LINK = "Link to the wiki: %url%";
	public static String WIKI_URL = "https://github.com/TheoKah/CarrotShop/wiki/User-Guide";

	public static String CMD_SPAM = "Shop use report: %status%";
	public static String CMD_CONFIG_RELOAD = "Config reloaded";
	public static String CMD_CONFIG_CURRENCY = "Default currency is %name% (%id)";

	public static String SHOP_PERM = "You don't have perms to build a [%type%] sign";
	public static String SHOP_CHEST = "%type% signs require a chest";
	public static String SHOP_CHEST2 = "%type% signs require two chests";
	public static String SHOP_LEVER = "%type% signs require a lever";
	public static String SHOP_DONE = "You have setup a [%type%] shop:";
	public static String SHOP_CHEST_EMPTY = "chest cannot be empty";
	public static String SHOP_PRICE = "bad price";
	public static String SHOP_FORMAT_BUY = "Buy %items% for %price%?";
	public static String SHOP_FORMAT_SELL = "Sell %items% for %price%?";
	public static String SHOP_FORMAT_TRADE = "Trade %giveItems% and get %getItems%?";

	public static String SHOP_EMPTY = "This shop is empty!";
	public static String SHOP_FULL = "This shop is full!";
	public static String SHOP_SCHRODINGER = "This shop is either full or empty!";
	public static String SHOP_ITEMS = "You don't have the items!";
	public static String SHOP_MONEY = "You don't have enough money!";
	public static String SHOP_OMONEY = "Shop owner don't have enough money!";
	public static String SHOP_ERROR_MONEY = "Unable to give you the money!";
	public static String SHOP_RECAP_BUY = "You bought %items% for %price%";
	public static String SHOP_RECAP_OBUY = "%player% bought %items% for %price% from you";
	public static String SHOP_RECAP_SELL = "You sold %items% for %price%";
	public static String SHOP_RECAP_OSELL = "%player% sold %items% for %price% to you";
	public static String SHOP_RECAP_TRADE = "You traded %giveItems% for %getItems%";
	public static String SHOP_RECAP_OTRADE = "%player% traded %giveItems% for %getItems%";
	public static String SHOP_BANK_HELP = "Right click to see your balance";
	public static String SHOP_BANK = "Your balance: %bank%";
	public static String SHOP_HEAL_HELP = "Heal for %price%?";
	public static String SHOP_HEAL_HELP_NOECON = "Heal?";
	public static String SHOP_HEAL = "You healed for %price%";
	public static String SHOP_HEAL_NOECON = "You Healed";
	public static String SHOP_DEVICEOFF_HELP = "Deactivate for %price%?";
	public static String SHOP_DEVICEOFF_HELP_NOECON = "Deactivate?";
	public static String SHOP_DEVICEOFF = "Device deactivated for %price%";
	public static String SHOP_DEVICEOFF_NOECON = "Device deactivated";
	public static String SHOP_DEVICEON_HELP = "Activate for %price%?";
	public static String SHOP_DEVICEON_HELP_NOECON = "Activate?";
	public static String SHOP_DEVICEON = "Device activated for %price%";
	public static String SHOP_DEVICEON_NOECON = "Device activated";
	public static String SHOP_TOGGLE_HELP = "Toggle for %price%?";
	public static String SHOP_TOGGLE_HELP_NOECON = "Toggle?";
	public static String SHOP_TOGGLE = "Device toggled for 2 seconds for %price%";
	public static String SHOP_TOGGLE_NOECON = "Device toggled for 2 seconds";
	public static String SHOP_DEVICE_OTHER = "%player% used your [%type%] sign for %price%";
	public static String SHOP_DEVICE_OTHER_NOECON = "%player% used your [%type%] sign";

	public static String CONSOLE_ERROR_PLAYER = "Need to be a player";
	public static String CONSOLE_ERROR_LOAD = "Errors occured while loading CarrotShops";
	public static String CONSOLE_ERROR_LOGS = "Unable to store logs for shop %owner% triggered by %source%: %error%";
	public static String CONSOLE_ERROR_GENERIC = "ERROR: %error%";

	private static ConfigurationLoader<CommentedConfigurationNode> languageManager;
	private static CommentedConfigurationNode language;

	static void init(File rootDir) {
		File languageFile = new File(rootDir, "language.conf");
		languageManager = HoconConfigurationLoader.builder().setPath(languageFile.toPath()).build();

		try {
			if (!languageFile.exists()) {
				languageFile.getParentFile().mkdirs();
				languageFile.createNewFile();
				language = languageManager.load();
				languageManager.save(language);
			}
			reload();
		} catch (IOException e) {
			CarrotShop.getLogger().error("Could not create language file!");
			e.printStackTrace();
		}
	}

	public static void reload() {
		try {
			language = languageManager.load();
		} catch (IOException e) {
			CarrotShop.getLogger().error("Could not load language file!");
			e.printStackTrace();
		}
		Field fields[] = Lang.class.getFields();
		for (Field field : fields) {
			if (field.getType() != String.class)
				continue;
			if (language.getNode(field.getName()).getString() != null) {
				try {
					field.set(String.class, language.getNode(field.getName()).getString());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					CarrotShop.getLogger().error("Error whey loading language string " + field.getName());
					e.printStackTrace();
				}
			} else {
				try {
					language.getNode(field.getName()).setValue(field.get(String.class));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					CarrotShop.getLogger().error("Error whey saving language string " + field.getName());
					e.printStackTrace();
				}
			}
		}

		save();
	}

	private static void save() {
		try {
			languageManager.save(language);
		} catch (IOException e) {
			CarrotShop.getLogger().error("Could not save lang file !");
		}
	}

	public static String split(String str, String separator, int pos) {
		String[] strs = str.split(separator);
		if (strs.length > pos)
			return strs[pos];
		return "";
	}

	public static Text build(String template, Map<String, Text> valueMap) {
		Text.Builder builder = Text.builder();

		Scanner sc = new Scanner(template);
		for (String s; (s = sc.findWithinHorizon("(?<=%)\\w*?(?=%)", 0)) != null; ) {
			String varName = "%" + s + "%";
			int varStart = template.indexOf(varName);
			int varEnd = varStart + varName.length();
			Text varText = valueMap.getOrDefault(s, Text.of(varName));

			builder.append(Text.of(template.substring(0, varStart))).append(varText);

			template = template.substring(varEnd);
		}

		if (template.length() > 0)
			builder.append(Text.of(template));

		return builder.build();
	}
}
