package model;
// Generated Dec 22, 2019 8:33:35 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Citas generated by hbm2java
 */
public class Citas  implements java.io.Serializable {


     private long idcitas;
     private String descripcion;
     private String notas;
     private Date auFechainsert;
     private Integer auUserinsert;
     private Date auFechaupdt;
     private Integer auUserupdt;
     private String nombre;
     private String apellido;
     private String telefono;
     private String activo;
     private String realizado;
     private String cancelado;
     private Date horainicio;
     private Date horafin;
     private Boolean confirmado;
     private Long iduser;

    public Citas() {
    }

	
    public Citas(long idcitas) {
        this.idcitas = idcitas;
    }
    public Citas(long idcitas, String descripcion, String notas, Date auFechainsert, Integer auUserinsert, Date auFechaupdt, Integer auUserupdt, String nombre, String apellido, String telefono, String activo, String realizado, String cancelado, Date horainicio, Date horafin, Boolean confirmado, Long iduser) {
       this.idcitas = idcitas;
       this.descripcion = descripcion;
       this.notas = notas;
       this.auFechainsert = auFechainsert;
       this.auUserinsert = auUserinsert;
       this.auFechaupdt = auFechaupdt;
       this.auUserupdt = auUserupdt;
       this.nombre = nombre;
       this.apellido = apellido;
       this.telefono = telefono;
       this.activo = activo;
       this.realizado = realizado;
       this.cancelado = cancelado;
       this.horainicio = horainicio;
       this.horafin = horafin;
       this.confirmado = confirmado;
       this.iduser = iduser;
    }
   
    public long getIdcitas() {
        return this.idcitas;
    }
    
    public void setIdcitas(long idcitas) {
        this.idcitas = idcitas;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getNotas() {
        return this.notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    public Date getAuFechainsert() {
        return this.auFechainsert;
    }
    
    public void setAuFechainsert(Date auFechainsert) {
        this.auFechainsert = auFechainsert;
    }
    public Integer getAuUserinsert() {
        return this.auUserinsert;
    }
    
    public void setAuUserinsert(Integer auUserinsert) {
        this.auUserinsert = auUserinsert;
    }
    public Date getAuFechaupdt() {
        return this.auFechaupdt;
    }
    
    public void setAuFechaupdt(Date auFechaupdt) {
        this.auFechaupdt = auFechaupdt;
    }
    public Integer getAuUserupdt() {
        return this.auUserupdt;
    }
    
    public void setAuUserupdt(Integer auUserupdt) {
        this.auUserupdt = auUserupdt;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getActivo() {
        return this.activo;
    }
    
    public void setActivo(String activo) {
        this.activo = activo;
    }
    public String getRealizado() {
        return this.realizado;
    }
    
    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }
    public String getCancelado() {
        return this.cancelado;
    }
    
    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }
    public Date getHorainicio() {
        return this.horainicio;
    }
    
    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }
    public Date getHorafin() {
        return this.horafin;
    }
    
    public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }
    public Boolean getConfirmado() {
        return this.confirmado;
    }
    
    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }
    public Long getIduser() {
        return this.iduser;
    }
    
    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }




}


