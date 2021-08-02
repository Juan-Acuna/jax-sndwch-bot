package com.jaxsandwich.discordbot.comandos;

import java.util.List;

import com.jaxsandwich.discordbot.conexion.CommandManager;
import com.jaxsandwich.discordbot.main.modelos.Guild;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.framework.annotations.*;
import com.jaxsandwich.framework.core.Values;
import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.models.CommandPacket;
import com.jaxsandwich.framework.models.InputParameter;
import com.jaxsandwich.framework.models.ModelCategory;
import com.jaxsandwich.framework.models.ModelCommand;
import com.jaxsandwich.framework.models.InputParameter.InputParamType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Category(id="Administracion")
public class Administracion {
	
	/*
	@Command(name="exFunar",desc="Permite funar a un miembro del servidor (asigna un rol previamente configurado como 'rol Funado', con los privilegios predefinidos por los administradores del servidor. Cada vez que alguien asigne otro rol a este usuario, automaticamente se los voy a quitar)",enabled=false,visible=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Funar. Se permiten mas de uno.")
	public static void exfunar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}*/
	
	@Command(id="Banear",enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a banear. Se permiten mas de uno.")
	@Option(id="tiempo",desc="Tiempo durante el cual el o los miembros sera baneado del servidor. La unidad de tiempo por defecto es en horas.")
	@Option(id="unidad",desc="Permite seleccionar la unidad de tiempo para la opción `tiempo`.\n\n`S` - Segundos\n`M` - Minutos\n`H` - Horas (por defecto)\n`D` - Dias")
	public static void banear(CommandPacket packet) {
		
	}
	
	@Command(id="LimpiarChat",enabled=false)
	@Parameter(name="Cantidad de mensajes",desc="Numero de mensajes a borrar del canal de texto. Debe ser un valor numerico valido entre 1 y 100, de lo contrario solo borraré el ultimo mensaje(sin contar el del comando).")
	public static void cleanchat(CommandPacket packet) {
		
	}
	
	@Command(id="Configurar",enabled=true)
	@Option(id="?",desc="Explicación y ejemplos de uso de los comandos de la configuración.")
	@Option(id="prefijo",desc="Personaliza el prefijo en este servidor.",alias={"p"})
	@Option(id="opcion",desc="Personaliza el prefijo de parametros/opciones en este servidor.",alias={"o","op"})
	@Option(id="idioma",desc="Configura el idioma del bot. Solo conozco dos idiomas: Español[ES] e Inglés[EN].",alias={"i","l"})
	@Option(id="bloquear",desc="Bloquea al objetivo en este servidor.\nSi el objetivo es un comando/categoría este no podrá ser usado en este servidor.\nSi el objetivo es un miembro/rol este no podrá usar comandos en este servidor.\nSi el objetivo es un canal en este no se podrán usar comandos.\n`El objetivo debe ser etiquetado(Canal, Miembro y Rol), excepto si este es un comando o una categoría.`",alias={"b","blq"})
	@Option(id="desbloquear",desc="Desbloquea al objetivo en este servidor.\nSi el objetivo es un comando/categoría este podrá ser usado en este servidor.\nSi el objetivo es un miembro/rol este podrá usar comandos en este servidor.\nSi el objetivo es un canal en este se podrán usar comandos.\n`El objetivo debe ser etiquetado(Canal, Miembro y Rol), excepto si este es un comando o una categoría.`",alias={"d","dsbl"})
	@Option(id="comandos",desc="Esta opción habilita el menú de configuración de los comandos en este servidor.",alias= {"cmd","cmds"},enabled=false)
	@Option(id="bot",desc="Esta opción habilita el menú de configuración del bot en este servidor.",alias= {"jax","sandwich"},enabled=false)
	@Option(id="censura",desc="Esta opción habilita el menú de configuración del control de contenido en este servidor.",enabled=false)
	@Option(id="alarmas",desc="Esta opción habilita el menú de configuración de alarmas en este servidor.",enabled=false)
	//@Option(name="",desc="",alias={""})
	public static void config(CommandPacket packet) throws Exception {
		if(!packet.isFromGuild())
			return;
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		Language lang = packet.getPreferredLang();
		Guild servidor = (Guild) packet.getModelGuild();
		List<Role> rl = e.getMember().getRoles();
		boolean admin = false;
		if(rl.size()>0) {
			for(Role r : rl) {
				if(r.hasPermission(Permission.ADMINISTRATOR)) {
					admin=true;
					break;
				}
			}
		}
		if(!admin) {
			packet.getTextChannel().sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-conf-no-admin", lang), "rojo")).queue();
			return;
		}
		boolean b = true;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(Values.value("jax-conf-act-conf", lang));
		for(InputParameter p : packet.getParameters()) {
			if(p.getType()==InputParamType.Standar) {
				if(p.getKey().equals("prefijo")) {
					String s = p.getValueAsString();
					if(s!=null) {
						if(s.split("\\s").length>1) {
							//invalido
							eb.setTitle(Values.value("jax-conf-pref-no-sp", lang));
						}else {
							servidor.setCustomPrefix(s);
						}
					}else {
						eb.setTitle(Values.value("jax-conf-ing-car-nc", lang));
					}
					b=false;
				}else if(p.getKey().equals("opcion")) {
					String s = p.getValueAsString();
					if(s!=null) {
						if(s.split("\\s").length>1) {
							//invalido
							eb.setTitle(Values.value("jax-conf-opt-pref-no-sp", lang));
						}else {
							servidor.setCustomOptionsPrefix(s);
						}
					}else {
						eb.setTitle(Values.value("jax-conf-ing-car-nc", lang));
					}
					b=false;
				}else if(p.getKey().equals("idioma")) {
					String s = p.getValueAsString();
					if(s.equalsIgnoreCase("es") || s.equalsIgnoreCase("en")) {
						servidor.setLanguage(Language.valueOf(s.toUpperCase()));
						eb.setTitle(Values.value("jax-conf-act-conf", servidor.getLanguage()));
					}else {
						eb.setTitle(Values.value("jax-val-incorrecto", lang));
					}
					b=false;
				}else if(p.getKey().equals("bloquear")) {
					List<TextChannel> lc = e.getMessage().getMentionedChannels();
					List<Role> lr = e.getMessage().getMentionedRoles();
					List<Member> lm = e.getMessage().getMentionedMembers();
					String[] ms = p.getValueAsString().replaceAll("@","").replaceAll("#","").replaceAll("\\s",",").split(",");
					if(ms.length>0) {
						int ci = 0;
						int i = 0;
						if(lc.size()>0) {
							for(TextChannel t : lc) {
								for(i = 0;i<ms.length;i++) {
									if(ms[i]!=null && (t.getId().equals(ms[i]) || t.getName().equals(ms[i]))) {
										servidor.setAllowedChannel(ms[i], false);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
								if(ci>=ms.length)
									break;
							}
						}
						if(lr.size()>0) {
							for(Role r : lr) {
								for(i = 0;i<ms.length;i++) {
									if(ms[i]!=null && (r.getId().equals(ms[i]) || r.getName().equals(ms[i]))) {
										servidor.setAllowedRole(ms[i], false);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
								if(ci>=ms.length)
									break;
							}
						}
						if(lm.size()>0) {
							for(Member m : lm) {
								for(i = 0;i<ms.length;i++) {
									if(ms[i]!=null && (m.getId().equals(ms[i]) || m.getEffectiveName().equals(ms[i]))) {
										servidor.setAllowedMember(ms[i], false);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
								if(ci>=ms.length)
									break;
							}
						}
						if(ci<ms.length) {
							for(i = 0;i<ms.length;i++) {
								if(ms[i]!=null) {
									if(ModelCategory.find(ms[i])!=null) {
										servidor.setAllowedCategory(ms[i], false);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
							}
						}
						if(ci<ms.length) {
							for(i = 0;i<ms.length;i++) {
								if(ms[i]!=null) {
									if(ModelCommand.find(ms[i])!=null) {
										servidor.setAllowedCommand(ms[i], false);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
							}
						}
					}else {
						eb.setTitle(Values.value("jax-conf-ing-objetivo", lang));
					}
					b=false;
				}else if(p.getKey().equals("desbloquear")) {
					List<TextChannel> lc = e.getMessage().getMentionedChannels();
					List<Role> lr = e.getMessage().getMentionedRoles();
					List<Member> lm = e.getMessage().getMentionedMembers();
					String[] ms = p.getValueAsString().replaceAll("\\s",",").split(",");
					if(ms.length>0) {
						int ci = 0;
						int i = 0;
						if(lc.size()>0) {
							for(TextChannel t : lc) {
								for(i = 0;i<ms.length;i++) {
									if(ms[i]!=null && t.getId().equals(ms[i])) {
										servidor.setAllowedChannel(ms[i], true);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
								if(ci>=ms.length)
									break;
							}
						}
						if(lr.size()>0) {
							for(Role r : lr) {
								for(i = 0;i<ms.length;i++) {
									if(ms[i]!=null && r.getId().equals(ms[i])) {
										servidor.setAllowedRole(ms[i], true);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
								if(ci>=ms.length)
									break;
							}
						}
						if(lm.size()>0) {
							for(Member m : lm) {
								for(i = 0;i<ms.length;i++) {
									if(ms[i]!=null && m.getId().equals(ms[i])) {
										servidor.setAllowedMember(ms[i], true);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
								if(ci>=ms.length)
									break;
							}
						}
						if(ci<ms.length) {
							for(i = 0;i<ms.length;i++) {
								if(ms[i]!=null) {
									if(ModelCategory.find(ms[i])!=null) {
										servidor.setAllowedCategory(ms[i], true);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
							}
						}
						if(ci<ms.length) {
							for(i = 0;i<ms.length;i++) {
								if(ms[i]!=null) {
									if(ModelCommand.find(ms[i])!=null) {
										servidor.setAllowedCommand(ms[i], true);
										ci++;
										if(ci>=ms.length)
											break;
									}
								}
							}
						}
					}else {
						eb.setTitle(Values.value("jax-conf-ing-objetivo", lang));
					}
					b=false;
				}else if(p.getKey().equals("comandos")) {
					
					b=false;
				}else if(p.getKey().equals("bot")) {
					
					b=false;
				}else if(p.getKey().equals("censura")) {
					
					b=false;
				}else if(p.getKey().equals("alarmas")) {
					
					b=false;
				}else if(p.getKey().equals("?")) {
					eb.setTitle(Values.value("jax-conf-?-titulo", lang));
					//eb.setDescription("");
					eb.addField(Values.value("jax-conf-?-f1-t", lang), Values.value("jax-conf-?-f1-d", lang), false);
					eb.addField(Values.value("jax-conf-?-f2-t", lang), Values.value("jax-conf-?-f2-d", lang), false);
					eb.addField(Values.value("jax-conf-?-f3-t", lang), Values.value("jax-conf-?-f3-d", lang), false);
					eb.addField(Values.value("jax-conf-?-f4-t", lang), Values.value("jax-conf-?-f4-d", lang), false);
					eb.addField(Values.value("jax-conf-?-f5-t", lang), Values.value("jax-conf-?-f5-d", lang), false);
					eb.setFooter(Values.value("jax-conf-?-footer", lang));
					b=false;
				}
			}
		}
		if(b) {
			eb.setTitle(Values.formatedValue("jax-conf-inf-titulo", lang, servidor.getLastKnownName()));
			eb.addField(Values.value("jax-conf-inf-pref-t", lang), (servidor.prefix!=null?servidor.prefix:Values.value("jax-conf-inf-pref-n", lang)), true);
			eb.addField(Values.value("jax-conf-inf-opt-pref-t", lang), (servidor.opt_prefix!=null?servidor.opt_prefix:Values.value("jax-conf-inf-pref-n", lang)), true);
			eb.addField(Values.value("jax-conf-inf-lang-t", lang), Values.formatedValue("jax-conf-inf-lang-d", lang,lang.name()), true);
			eb = getInfoComandos(servidor, lang, eb);
			eb.addBlankField(true);
			eb = getInfoCategorias(servidor, lang, eb);
			eb = getInfoRoles(servidor, lang, eb,e);
			eb = getInfoCanales(servidor, lang, eb,e);
			eb = getInfoMiembros(servidor, lang, eb,e);
		}else {
			servidor.push();
			if(!CommandManager.update(servidor))
				eb.setTitle(Values.value("jax-conf-conf-no-aplicada", lang));
		}
		packet.getTextChannel().sendMessageEmbeds(eb.build()).queue();
	}
	protected static EmbedBuilder getInfoComandos(Guild servidor, Language lang, EmbedBuilder eb) {
		String str = "";//int i = 1;
		if(servidor.isDefaultDenyCommands()) {
			if(servidor.getAllowedCommands().size()>0) {
				for(String k : servidor.getAllowedCommands().keySet()) {
					if(servidor.isCommandAllowed(k)) {
						str +="`"+ModelCommand.find(k).getName(lang)+"` ";/*if(i>2){i=0;str+="\n";}else{str+=" ";}i++;*/
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-n-alwd-cmd", lang), str, true);
		}else {
			if(servidor.getAllowedCommands().size()>0) {
				for(String k : servidor.getAllowedCommands().keySet()) {
					if(!servidor.isCommandAllowed(k)) {
						str +="`"+ModelCommand.find(k).getName(lang)+"` ";
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-alwd-cmd", lang), str, true);
		}
		return eb;
	}
	protected static EmbedBuilder getInfoCategorias(Guild servidor, Language lang, EmbedBuilder eb) {
		String str = "";
		if(servidor.isDefaultDenyCategories()) {
			if(servidor.getAllowedCategories().size()>0) {
				for(String k : servidor.getAllowedCategories().keySet()) {
					if(servidor.isCategoryAllowed(k))
						str +="`"+ModelCategory.find(k).getName(lang)+"` ";
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-n-alwd-cat", lang), str, true);
		}else {
			if(servidor.getAllowedCategories().size()>0) {
				for(String k : servidor.getAllowedCategories().keySet()) {
					if(!servidor.isCategoryAllowed(k))
						str +="`"+ModelCategory.find(k).getName(lang)+"` ";
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-alwd-cat", lang), str, true);
		}
		return eb;
	}
	protected static EmbedBuilder getInfoRoles(Guild servidor, Language lang, EmbedBuilder eb,MessageReceivedEvent e) {
		String str = "";
		if(servidor.isDefaultDenyRoles()) {
			if(servidor.getAllowedRoles().size()>0) {
				for(String k : servidor.getAllowedRoles().keySet()) {
					if(servidor.isRoleAllowed(k)) {
						Role r = e.getGuild().getRoleById(k);
						if(r!=null) {
							str +="`"+r.getName()+"` ";
						}
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-n-alwd-rol", lang), str, true);
		}else {
			if(servidor.getAllowedRoles().size()>0) {
				for(String k : servidor.getAllowedRoles().keySet()) {
					if(!servidor.isRoleAllowed(k)) {
						Role r = e.getGuild().getRoleById(k);
						if(r!=null) {
							str +="`"+r.getName()+"` ";
						}
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-alwd-rol", lang), str, true);
		}
		return eb;
	}
	protected static EmbedBuilder getInfoCanales(Guild servidor, Language lang, EmbedBuilder eb,MessageReceivedEvent e) {
		String str = "";
		if(servidor.isDefaultDenyChannels()) {
			if(servidor.getAllowedChannels().size()>0) {
				for(String k : servidor.getAllowedChannels().keySet()) {
					if(servidor.isChannelAllowed(k)) {
						TextChannel t = e.getGuild().getTextChannelById(k);
						if(t!=null) {
							if(t.getName()==null)
								continue;
							str +="`"+t.getName()+"` ";
						}
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-n-alwd-chn", lang), str, true);
		}else {
			if(servidor.getAllowedChannels().size()>0) {
				for(String k : servidor.getAllowedChannels().keySet()) {
					if(!servidor.isChannelAllowed(k)) {
						TextChannel t = e.getGuild().getTextChannelById(k);
						if(t!=null) {
							if(t.getName()==null)
								continue;
							str +="`"+t.getName()+"` ";
						}
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-alwd-chn", lang), str, true);
		}
		return eb;
	}
	protected static EmbedBuilder getInfoMiembros(Guild servidor, Language lang, EmbedBuilder eb,MessageReceivedEvent e) {
		String str = "";
		if(servidor.isDefaultDenyMembers()) {
			if(servidor.getAllowedMembers().size()>0) {
				for(String k : servidor.getAllowedMembers().keySet()) {
					if(servidor.isMemberAllowed(k)) {
						Member m = e.getGuild().getMemberById(k);
						if(m!=null) {
							str +="`"+m.getEffectiveName()+"` ";
						}
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-n-alwd-mbr", lang), str, true);
		}else {
			if(servidor.getAllowedMembers().size()>0) {
				for(String k : servidor.getAllowedMembers().keySet()) {
					if(!servidor.isMemberAllowed(k)) {
						Member m = e.getGuild().getMemberById(k);
						if(m!=null) {
							str +="`"+m.getEffectiveName()+"` ";
						}
					}
				}
			}else {
				str = Values.value("jax-conf-inf-alwd-v", lang);
			}
			eb.addField(Values.value("jax-conf-inf-alwd-mbr", lang), str, true);
		}
		return eb;
	}
}
