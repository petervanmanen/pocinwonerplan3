import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './speeltoestel.reducer';

export const SpeeltoestelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const speeltoestelEntity = useAppSelector(state => state.speeltoestel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="speeltoestelDetailsHeading">Speeltoestel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{speeltoestelEntity.id}</dd>
          <dt>
            <span id="catalogusprijs">Catalogusprijs</span>
          </dt>
          <dd>{speeltoestelEntity.catalogusprijs}</dd>
          <dt>
            <span id="certificaat">Certificaat</span>
          </dt>
          <dd>{speeltoestelEntity.certificaat ? 'true' : 'false'}</dd>
          <dt>
            <span id="certificaatnummer">Certificaatnummer</span>
          </dt>
          <dd>{speeltoestelEntity.certificaatnummer}</dd>
          <dt>
            <span id="certificeringsinstantie">Certificeringsinstantie</span>
          </dt>
          <dd>{speeltoestelEntity.certificeringsinstantie}</dd>
          <dt>
            <span id="controlefrequentie">Controlefrequentie</span>
          </dt>
          <dd>{speeltoestelEntity.controlefrequentie}</dd>
          <dt>
            <span id="datumcertificaat">Datumcertificaat</span>
          </dt>
          <dd>
            {speeltoestelEntity.datumcertificaat ? (
              <TextFormat value={speeltoestelEntity.datumcertificaat} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gemakkelijktoegankelijk">Gemakkelijktoegankelijk</span>
          </dt>
          <dd>{speeltoestelEntity.gemakkelijktoegankelijk ? 'true' : 'false'}</dd>
          <dt>
            <span id="inspectievolgorde">Inspectievolgorde</span>
          </dt>
          <dd>{speeltoestelEntity.inspectievolgorde}</dd>
          <dt>
            <span id="installatiekosten">Installatiekosten</span>
          </dt>
          <dd>{speeltoestelEntity.installatiekosten}</dd>
          <dt>
            <span id="speelterrein">Speelterrein</span>
          </dt>
          <dd>{speeltoestelEntity.speelterrein}</dd>
          <dt>
            <span id="speeltoesteltoestelonderdeel">Speeltoesteltoestelonderdeel</span>
          </dt>
          <dd>{speeltoestelEntity.speeltoesteltoestelonderdeel}</dd>
          <dt>
            <span id="technischelevensduur">Technischelevensduur</span>
          </dt>
          <dd>{speeltoestelEntity.technischelevensduur}</dd>
          <dt>
            <span id="toestelcode">Toestelcode</span>
          </dt>
          <dd>{speeltoestelEntity.toestelcode}</dd>
          <dt>
            <span id="toestelgroep">Toestelgroep</span>
          </dt>
          <dd>{speeltoestelEntity.toestelgroep}</dd>
          <dt>
            <span id="toestelnaam">Toestelnaam</span>
          </dt>
          <dd>{speeltoestelEntity.toestelnaam}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{speeltoestelEntity.type}</dd>
          <dt>
            <span id="typenummer">Typenummer</span>
          </dt>
          <dd>{speeltoestelEntity.typenummer}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{speeltoestelEntity.typeplus}</dd>
          <dt>
            <span id="typeplus2">Typeplus 2</span>
          </dt>
          <dd>{speeltoestelEntity.typeplus2}</dd>
          <dt>
            <span id="valruimtehoogte">Valruimtehoogte</span>
          </dt>
          <dd>{speeltoestelEntity.valruimtehoogte}</dd>
          <dt>
            <span id="valruimteomvang">Valruimteomvang</span>
          </dt>
          <dd>{speeltoestelEntity.valruimteomvang}</dd>
          <dt>
            <span id="vrijevalhoogte">Vrijevalhoogte</span>
          </dt>
          <dd>{speeltoestelEntity.vrijevalhoogte}</dd>
        </dl>
        <Button tag={Link} to="/speeltoestel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/speeltoestel/${speeltoestelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpeeltoestelDetail;
