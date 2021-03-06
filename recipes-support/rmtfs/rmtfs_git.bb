SUMMARY = "RMTFS QMI service"
HOMEPAGE = "https://github.com/andersson/mrtfs.git"
SECTION = "devel"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ca25dbf5ebfc1a058bfc657c895aac2f"

inherit systemd

SRCREV = "0d3c49ec5aa27517afec9f18a3afc4b0800e6cbb"
SRC_URI = "git://github.com/andersson/${BPN}.git;branch=master;protocol=https"
SRC_URI += "file://rmtfs.service"
DEPENDS = "qmic-native qrtr udev"

PV = "0.0+${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'LDFLAGS=${TARGET_LDFLAGS} -L${STAGING_LIBDIR} -lqrtr -ludev'"

do_install () {
    oe_runmake install DESTDIR=${D} prefix=${prefix}

    sed -i -e s:/usr/bin:${bindir}:g ${WORKDIR}/rmtfs.service
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/rmtfs.service ${D}${systemd_unitdir}/system
}

SYSTEMD_SERVICE_${PN} = "rmtfs.service"
RDEPENDS_${PN} += "qrtr"
