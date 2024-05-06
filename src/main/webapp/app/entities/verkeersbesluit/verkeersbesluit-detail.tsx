import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verkeersbesluit.reducer';

export const VerkeersbesluitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verkeersbesluitEntity = useAppSelector(state => state.verkeersbesluit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verkeersbesluitDetailsHeading">Verkeersbesluit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verkeersbesluitEntity.id}</dd>
          <dt>
            <span id="datumbesluit">Datumbesluit</span>
          </dt>
          <dd>
            {verkeersbesluitEntity.datumbesluit ? (
              <TextFormat value={verkeersbesluitEntity.datumbesluit} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{verkeersbesluitEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{verkeersbesluitEntity.datumstart}</dd>
          <dt>
            <span id="huisnummer">Huisnummer</span>
          </dt>
          <dd>{verkeersbesluitEntity.huisnummer}</dd>
          <dt>
            <span id="postcode">Postcode</span>
          </dt>
          <dd>{verkeersbesluitEntity.postcode}</dd>
          <dt>
            <span id="referentienummer">Referentienummer</span>
          </dt>
          <dd>{verkeersbesluitEntity.referentienummer}</dd>
          <dt>
            <span id="straat">Straat</span>
          </dt>
          <dd>{verkeersbesluitEntity.straat}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{verkeersbesluitEntity.titel}</dd>
          <dt>Isvastgelegdin Document</dt>
          <dd>{verkeersbesluitEntity.isvastgelegdinDocument ? verkeersbesluitEntity.isvastgelegdinDocument.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/verkeersbesluit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verkeersbesluit/${verkeersbesluitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerkeersbesluitDetail;
