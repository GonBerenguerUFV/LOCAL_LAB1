package com.example.demo;

public class Community {

    private String _id;
    private From from;
    private To to;
    private TimeRange timeRange;
    private Long total;

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    // Constructors
    public Community(String id, String fromComunidad, String fromProvincia, String toComunidad, String toProvincia, String period, Long total) {
        this._id = id;
        this.from = new From(fromComunidad, fromProvincia);
        this.to = new To(toComunidad, toProvincia);
        this.timeRange = new TimeRange(period);
        this.total = total;
    }

    public Community(String id, From from, To to, TimeRange timeRange, long total) {
        this._id = id;
        this.from = from;
        this.to = to;
        this.timeRange = timeRange;
        this.total = total;
    }

    // The default constructor is needed in order to correctly deserialize the POST / PUT requests
    public Community() {

    }

    // Pretty print the object
    @Override
    public String toString() {
        return "Community{" +
                "_id='" + _id + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", timeRange=" + timeRange +
                ", total=" + total + '\'' +
                '}';
    }

    // Class for the field 'from'
    class From {
        private String comunidad;
        private String provincia;
        
        // Getters and Setters for the field 'from'
        public String getComunidad() {
            return comunidad;
        }

        public void setComunidad(String comunidad) {
            this.comunidad = comunidad;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        // Constructors
        public From(String comunidad, String provincia) {
            this.comunidad = comunidad;
            this.provincia = provincia;
        }

        // The default constructor is needed in order to correctly deserialize the POST / PUT requests
        public From() {

        }

        @Override
        public String toString() {
            return "From{" +
                    "comunidad='" + comunidad + '\'' +
                    ", provincia='" + provincia + '\'' +
                    '}';
        }
    }

    // Class for the field 'to'
    class To {
        private String comunidad;
        private String provincia;

        // Getters and Setters for the field 'to'

        public String getComunidad() {
            return comunidad;
        }

        public void setComunidad(String comunidad) {
            this.comunidad = comunidad;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        // Constructors
        public To(String comunidad, String provincia) {
            this.comunidad = comunidad;
            this.provincia = provincia;
        }

        // The default constructor is needed in order to correctly deserialize the POST / PUT requests
        public To() {

        }

        // Overriding the toString method (pretty print)
        @Override
        public String toString() {
            return "To{" +
                    "communidad='" + comunidad + '\'' +
                    ", provincia='" + provincia + '\'' +
                    '}';
        }
    }

    // Class for the field 'timeRange'
    class TimeRange {
        private String fecha_inicio ;
        private String fecha_fin;
        private String period;

        // Getters and Setters for the field 'timeRange'

        public String getFecha_inicio() {
            return fecha_inicio ;
        }

        public void setFecha_inicio(String fecha_inicio ) {
            this.fecha_inicio  = fecha_inicio ;
        }

        public String getFecha_fin() {
            return fecha_fin;
        }

        public void setFecha_fin(String fecha_fin) {
            this.fecha_fin = fecha_fin;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        // Constructors
        public TimeRange(String period) {
            this.period = period;
            String[] dateRange = timeRangeFromPeriod(period);
            this.fecha_inicio  = dateRange[0];
            this.fecha_fin = dateRange[1];
        }

        public TimeRange(String fecha_inicio , String fecha_fin, String period) {
            this.period = period;
            this.fecha_inicio  = fecha_inicio ;
            this.fecha_fin = fecha_fin;
        }
        
        // The default constructor is needed in order to correctly deserialize the POST / PUT requests
        public TimeRange() {

        }
        
        private String[] timeRangeFromPeriod(String period) {
            // Suponiendo que el formato del periodo es algo como "202206" (año + mes)
            String year = period.substring(0, 4);
            String month = period.substring(5, 7);
            String fecha_inicio  = year + "/" + month + "/01";
            String to = year + "/" + month + "/30"; // Ajusta si el mes tiene 31 días o es febrero
            return new String[]{fecha_inicio , to};
        }

        // Overriding the toString method (pretty print)
        @Override
        public String toString() {
            return "TimeRange{" +
                    "fecha_inicio ='" + fecha_inicio  + '\'' +
                    ", to='" + to + '\'' +
                    ", period='" + period + '\'' +
                    '}';
        }

    }
}

