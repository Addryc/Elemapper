//      mon/${monster.fileName}.c
//      ${monster.codeDescription}
//      From the Elephant Mudlib
//      AutoCoded by EleMapper at ${monster.updated}

#include "../include/include.h"

inherit MONSTER;

void create() {
    ::create();
    set_name("${monster.name}");
    set_short("${monster.getShortDescWrapped()}");
    set_long("${monster.getLongDescWrapped()}");
    <#list monster.ids>
    set_id(({
        <#items as id>"${id}"<#sep>, </#items>
    }));
    </#list>
    set_race("${monster.race}");
    set_body_type("${monster.bodyType}");
    set_gender("${monster.gender}");
    set_level(${monster.level});
    <#if monster.primaryClass!="">
    set_class("${monster.primaryClass}");
    </#if>
    <#if monster.secondClass!="">
    set_second_class("${monster.secondClass}");
    </#if>
    <#list monster.renderEmotes()>
    set_emotes(${monster.emoteFrequency},({
        <#items as emote>"${emote}"<#sep>, </#items>
    }),0);
    </#list>
}