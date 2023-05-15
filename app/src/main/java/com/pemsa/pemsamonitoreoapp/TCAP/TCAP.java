package com.pemsa.pemsamonitoreoapp.TCAP;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.pemsa.pemsamonitoreoapp.R;

public class TCAP {
    public void terminosycondiciones(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        View view= inflater.inflate(R.layout.dialog_terminos_y_condiciones, null);
        TextView t1=(TextView)view.findViewById(R.id.APri1);
        TextView t2=(TextView)view.findViewById(R.id.APri2);
        TextView t3=(TextView)view.findViewById(R.id.APri3);
        TextView t4=(TextView)view.findViewById(R.id.APri4);
        TextView t5=(TextView)view.findViewById(R.id.APri5);
        TextView t6=(TextView)view.findViewById(R.id.APri6);
        TextView t7=(TextView)view.findViewById(R.id.APri7);
        TextView t8=(TextView)view.findViewById(R.id.APri8);
        TextView t9=(TextView)view.findViewById(R.id.APri9);
        TextView t10=(TextView)view.findViewById(R.id.APri10);
        TextView t11=(TextView)view.findViewById(R.id.APri11);
        TextView t12=(TextView)view.findViewById(R.id.APri12);


        builder.setView(view)
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        t1.setText(Html.fromHtml("<b>Protección Electrónica Monterrey SA de CV</b>, en lo sucesivo <b>PEMSA</b> en su carácter de responsable encargado del tratamiento de sus datos personales, manifiesta que, para efectos del presente aviso, su domicilio se encuentra ubicado en la calle 33 Poniente 307, Colonia Chulavista, C.P. 72420; Puebla, Puebla, México. "));
        t2.setText(Html.fromHtml("<b>PEMSA</b> presenta este aviso de privacidad con la finalidad de dar cumplimiento a los artículos 15, 16 y demás relativos de la Ley Federal de Protección de Datos Personales en Posesión de los Particulares (LA LEY)."));
        t3.setText(Html.fromHtml("<b>PEMSA</b> se compromete a que estos datos serán tratados bajo las más estrictas medidas de seguridad que garanticen su confidencialidad."));
        t4.setText(Html.fromHtml("Los referidos datos personales se incorporarán a las distintas bases de datos de <b>PEMSA</b>, razón por la cual <b>EL CLIENTE</b> autoriza que <b>PEMSA</b> realice el tratamiento de sus datos personales de conformidad con las finalidades más adelante descritas, para utilizarlos durante el desarrollo de las operaciones y servicios contratados. <b>PEMSA</b> tendrá la responsabilidad de protegerlos mientras estén en su poder."));
        t5.setText(Html.fromHtml("<b>1. Datos Personales Recibidos del Cliente</b><br> \n" +
                " \n" +
                "<br><b>EL CLIENTE</b> acepta de manera expresa entregar a <b>PEMSA</b> para su tratamiento, de manera enunciativa más no limitativa los siguientes datos personales:\n"));
        t6.setText(Html.fromHtml("\ta) Datos generales del titular y/o personal autorizado: Apellido Paterno, Apellido Materno, Nombre(s), identificación oficial, correo electrónico, empresa donde labora y compañía que representa." +
                " \n<br>"+
                "\tb) Domicilio: Calle, número exterior, número interior, colonia, código postal, Estado, delegación/municipio, teléfono, correo electrónico." +
                "\n"+
                "<br><br><b>Datos Personales Sensibles.</b> - Para cumplir con las finalidades previstas en este Aviso de Privacidad, <b>PEMSA</b> hace de su conocimiento que no se tratarán datos personales sensibles: \n" +
                "<br>En caso contrario se le darán los avisos respectivos para recabar debidamente su consentimiento expreso.\n" +
                "<br><br>Estos datos serán tratados bajo las más estrictas medidas de seguridad que garanticen su confidencialidad.\n" +
                "<br><br>Podrán tratarse otros datos personales, sensibles y no sensibles, que no se incluyan en las listas anteriores siempre y cuando dichos datos se consideren de la misma naturaleza y no sean excesivos respecto a las finalidades para los cuales se recaban.\n"));
        t7.setText(Html.fromHtml("<b>2. Finalidad de los Datos Personales</b>  \n" +
                "<br><br><b>El CLIENTE</b> consiente que sus datos personales sean utilizados por <b>PEMSA</b> con la <b>finalidad principal</b> de: \n" +
                "<br>\n" +
                "<br>1.\tIdentificación, localización y contacto con el titular y encargados; \n" +
                "<br>2.\tVerificar y confirmar su identidad; \n" +
                "<br>3.\tMonitoreo de sus sistemas de alarmas; \n" +
                "<br>4.\tConsulta de los eventos recibidos en nuestra central de monitoreo; \n" +
                "<br>5.\tAtención sus solicitudes, quejas, dudas y/o comentarios relacionados con nuestros servicios; \n" +
                "<br>6.\tLas demás finalidades que resulten necesarias para la prestación de los servicios por usted requeridos.\n" +
                "<br>\n" +
                "<br><b>Finalidades Secundarias</b>  \n" +
                "<br>1.\tRealizar estudios y procesos internos.  \n" +
                "<br>2.\tRealizar encuestas de calidad en el servicio y atención a clientes. \n" +
                "<br>3.\tPara fines mercadotécnicos, publicitarios o de prospección comercial. \n" +
                "<br>4.\tCumplir con los requisitos legales y reglamentarios aplicables. \n" +
                "<br>\n" +
                "<br>En caso de que no desee que sus datos personales sean tratados para estas finalidades secundarias, usted tiene un plazo máximo de 5 (cinco) días hábiles para comunicar lo anterior a la Dirección de Contacto: <b>datospersonales@pem-sa.com</b> La negativa para el uso de sus datos personales para estas finalidades secundarias, no podrá ser un motivo para que le neguemos los servicios y productos que solicita o contrata con nosotros.\n"));
        t8.setText(Html.fromHtml("<b>3.\tSeguridad de los Datos Personales</b>  \n" +
                "<br><br><b>PEMSA</b> implementará las medidas de seguridad técnicas, administrativas y físicas necesarias para procurar la integridad de sus datos personales y evitar su daño, pérdida, alteración, destrucción o el uso, acceso o tratamiento no autorizado. \n" +
                "<br><br>Únicamente el personal autorizado, que ha cumplido y observado los correspondientes requisitos de confidencialidad, podrá participar en el tratamiento de sus datos personales. El personal autorizado tiene prohibido permitir el acceso de personas no autorizadas  y/o utilizar los  datos personales  para fines  distintos  a los  establecidos  en el  presente Aviso de Privacidad.  La obligación de confidencialidad de las personas que participan en el tratamiento de sus datos personales subsiste aún después de terminada la relación con <b>PEMSA</b>.\n"));
        t9.setText(Html.fromHtml("<b>4.\tTransferencias</b> \n" +
                "<br>\n" +
                "<br>Para efectos de este aviso de privacidad en la Aplicación móvil (APP) no se harán transferencias de sus datos personales a terceros. En caso contrario se pondrán a su disposición los documentos necesarios a fin de recabar su consentimiento expreso. \n" +
                "<br> \n" +
                "<br>Se podrán transmitir sus datos personales a las autoridades competentes, locales y federales cuando se encuentre dentro de las excepciones señaladas en la Ley y su Reglamento. En el caso de transferencias, tratamiento de sus datos personales sensibles, financieros y bancarios, se requerirá su consentimiento expreso, mediante la firma del aviso de privacidad respectivo. \n" +
                "<br> \n" +
                "<br><b>PEMSA</b> podrá transmitir libremente los datos personales de <b>EL CLIENTE</b> a las sociedades controladoras, subsidiarias o filiales, a una sociedad matriz o a cualquier sociedad de <b>PEMSA </b>que opere bajo los mismos procesos y políticas internas. \n"));
        t10.setText(Html.fromHtml("<b>5.\tLimitaciones de la Divulgación de Información</b>\n" +
                "<br>\n" +
                "<br><b>PEMSA</b> se compromete a no transferir su información personal a terceros adicionales a los mencionados en el numeral anterior sin su consentimiento y en caso de que <b>EL CLIENTE</b> haya consentido que se realicen transferencias, <b>PEMSA</b> hará del conocimiento del <b>CLIENTE</b> a través de medios impresos o electrónicos la finalidad por la que dicha información será transferida a terceros; asimismo <b>PEMSA</b> informará a través de los mismos medios por los que se recabaron los datos personales (domicilio físico y/o dirección de correo electrónico) los cambios que se realicen al aviso de privacidad. \n" +
                "<br> \n" +
                "<br>De igual forma <b>EL CLIENTE</b> se compromete a dar aviso a <b>PEMS</b>A sobre cualquier cambio respecto a su domicilio físico y/o dirección de correo electrónico, o personas autorizadas a usar la APP con la finalidad de que <b>PEMSA</b> pueda comunicarse con <b>EL CLIENTE</b> para informar cualquier cambio o modificación respecto de lo contenido en el presente aviso de privacidad. \n" +
                "<br> \n" +
                "<br><b>PEMSA</b> no necesitará el consentimiento de <b>EL CLIENTE</b> para transferir a terceras personas su información en los siguientes casos: \n" +
                "<br> \n" +
                "<br>a)\tCuando la transferencia esté prevista en una Ley o Tratado en los que México sea parte. \n" +
                "<br> \n" +
                "<br>b)\tCuando la transferencia sea necesaria para la prevención o el diagnóstico médico, la prestación de asistencia sanitaria, tratamiento médico o la gestión de servicios sanitarios. \n" +
                "<br> \n" +
                "<br>c)\tCuando sea requerida por autoridades competentes de conformidad con las disposiciones legales aplicables. \n"));
        t11.setText(Html.fromHtml("<b>6.  Medios para ejercer los derechos de acceso, rectificación, cancelación y oposición (ARCO) de los datos personales</b> \n" +
                "<br> \n" +
                "<br><b>PEMSA</b> ha designado a un encargado de datos personales, (el “oficial de Privacidad”), por lo tanto, usted podrá limitar el uso o divulgación de sus datos personales mediante comunicación dirigida al Oficial de Privacidad al correo electrónico siguiente: \n" +
                "<br><b>datospersonales@pem-sa.com</b> (la “Dirección de Contacto”). \n" +
                "<br> \n" +
                "<br>Usted tiene derecho de: (i) acceder a sus datos personales en nuestro poder y conocer los detalles del tratamiento de los mismos, (ii) rectificarlos en caso de ser inexactos o incompletos, (iii) cancelarlos cuando considere que no se requieren para alguna de las finalidades señaladas en el presente Aviso de Privacidad, estén siendo utilizados para finalidades no consentidas o haya finalizado la relación contractual o de servicio, o (iv) oponerse al tratamiento de los mismos para fines específicos, según lo diga la ley, (conjuntamente, los “Derechos ARCO”). \n" +
                "<br> \n" +
                "<br>Los Derechos ARCO se ejercerán mediante la presentación de la solicitud respectiva, la cual deberá ser solicitada al Oficial de privacidad al correo: <b>datospersonales@pem-sa.com</b> acompañada de la siguiente información y documentación: \n" +
                "<br> \n" +
                "<br>I.\tSu nombre, domicilio y correo electrónico para poder comunicarle la respuesta a la Solicitud ARCO; \n" +
                "<br> \n" +
                "<br>II.\tLos documentos que acrediten su identidad (copia de IFE, pasaporte o cualquier otra identificación oficial) o en su caso, los documentos que acrediten su representación legal; \n" +
                "<br>\n" +
                "<br>III.\tUna descripción clara y precisa de los datos personales respecto de los cuales busca ejercer alguno de los Derechos ARCO; \n" +
                "<br> \n" +
                "<br>IV.\tCualquier documento o información que facilite la localización de sus datos personales; \n" +
                "<br> \n" +
                "<br>V.\tEn caso de solicitar una rectificación de datos, deberá de indicar también, las modificaciones a realizarse y aportar la documentación que sustente su petición; y \n" +
                "<br>\n" +
                "<br>VI.\tLa indicación del lugar donde podremos revisar los originales de la documentación que acompañe. \n" +
                "<br>\n" +
                "<br>Su Solicitud ARCO será contestada mediante un correo electrónico por parte del Oficial de Privacidad en un plazo máximo de 20 (veinte) días hábiles contados desde el día en que se haya recibido su Solicitud ARCO. En caso de que la Solicitud ARCO se conteste de manera afirmativa o procedente, tales cambios se harán en un plazo máximo de 15 (quince) días hábiles. Los plazos referidos en este párrafo se podrán prorrogar por una vez por un periodo igual en caso de ser necesario. \n" +
                "<br>\n" +
                "<br>Es importante comunicarle que <b>PEMSA</b> podrá negar el acceso (la “Negativa”) para que usted ejerza sus derechos ARCO en los siguientes supuestos: \n" +
                "<br>\n" +
                "<br>I.\tCuando Usted no sea el titular de los datos personales, o su representante legal no esté debidamente acreditado para ejercer por medio de él, sus Derechos ARCO; \n" +
                "<br> \n" +
                "<br>II.\tCuando en nuestra base de datos no se encuentren sus datos personales; \n" +
                "<br> \n" +
                "<br>III.\tCuando se lesionen los derechos de un tercero; \n" +
                "<br> \n" +
                "<br>IV.\tCuando exista un impedimento legal o la resolución de una autoridad competente, que restrinja sus Derechos ARCO; y \n" +
                "<br> \n" +
                "<br>V.\tCuando la Rectificación, Cancelación u Oposición haya sido previamente realizada. \n" +
                "<br> \n" +
                "<br>En relación con lo anterior, la Negativa podrá ser parcial, en cuyo caso <b>PEMSA</b> efectuará el acceso, rectificación, cancelación u oposición en la parte procedente. \n" +
                "<br> \n" +
                "<br><b>PEMSA</b> siempre le informará el motivo de su decisión y se la comunicará a Usted o en su caso, a su representante legal, en los plazos anteriormente establecidos, por correo electrónico, acompañando, en su caso, las pruebas que resulten pertinentes. \n" +
                "<br> \n" +
                "<br>El ejercicio de los Derechos ARCO será gratuito, previa acreditación de su identidad ante el responsable, pero si Usted reitera su solicitud en un periodo menor a doce meses, los costos serán de tres días de la Unidad de Medida y Actualización Vigente, más I.V.A., a menos que existan modificaciones sustanciales al Aviso de Privacidad que motiven nuevas consultas. En todos los casos, la entrega de los datos personales será gratuita, con la excepción de que Usted deberá de cubrir los gastos justificados de envío o el costo de reproducción en copias u otros formatos. \n" +
                "<br>\n" +
                "<br><b>EL CLIENTE</b> podrá revocar el consentimiento que ha otorgado a PEMSA para el tratamiento de los datos personales que no sean indispensables para el cumplimiento de las obligaciones derivadas del vínculo jurídico que les une, a fin de que <b>PEMSA</b> deje de hacer uso de los mismos. Para ello, es necesario que <b>EL CLIENTE</b> presente su petición en los términos antes mencionados.\n"));
        t12.setText(Html.fromHtml("<b>7.  Mecanismo para revocación del consentimiento.</b>\n" +
                "<br>\n" +
                "<br>En caso de que Usted decida revocar su consentimiento para que <b>PEMSA</b> deje de llevar a cabo el tratamiento de sus datos personales, o se oponga a la transferencia de los mismos, deberá de enviar una solicitud de revocación de consentimiento a la Dirección de Contacto, y deberá de ser acompañada en el correo electrónico de los documentos que acrediten su identidad (copia de IFE, pasaporte o cualquier otra identificación oficial) o en su caso, los documentos que acrediten su representación legal y la indicación del lugar en el cual se pone a nuestra disposición los documentos originales. \n" +
                "<br>\n" +
                "<br>Para conocer el procedimiento y requisitos para la revocación del consentimiento, usted podrá ponerse en contacto con nuestro Oficial de Privacidad en el correo electrónico siguiente: <b>datospersonales@pem-sa.com</b> \n" +
                "<br>\n" +
                "<br>De conformidad con lo dispuesto en el Reglamento (UE) 2016/679 del Parlamento Europeo y del Consejo de 27 de abril de 2016 relativo a la protección de las personas físicas en lo que respecta al tratamiento de datos personales y a la libre circulación de estos datos (en adelante “Reglamento general de protección de datos” o “GDPR”), <b>PEMSA</b> se compromete a seguir obteniendo su consentimiento expreso para adquirir, procesar y tratar sus datos personales de conformidad con lo establecido en el GDPR. \n" +
                "<br>\n" +
                "<br>El presente Aviso de Privacidad y sus cambios será publicado en las oficinas de <b>PEMSA</b> y/o en la página electrónica siguiente: <b>www.pem-sa.com</b>\n"));

        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        alert.show();
    }
}
