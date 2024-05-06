import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './besluit.reducer';

export const BesluitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const besluitEntity = useAppSelector(state => state.besluit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="besluitDetailsHeading">Besluit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{besluitEntity.id}</dd>
          <dt>
            <span id="besluit">Besluit</span>
          </dt>
          <dd>{besluitEntity.besluit}</dd>
          <dt>
            <span id="besluitidentificatie">Besluitidentificatie</span>
          </dt>
          <dd>{besluitEntity.besluitidentificatie}</dd>
          <dt>
            <span id="besluittoelichting">Besluittoelichting</span>
          </dt>
          <dd>{besluitEntity.besluittoelichting}</dd>
          <dt>
            <span id="datumbesluit">Datumbesluit</span>
          </dt>
          <dd>{besluitEntity.datumbesluit}</dd>
          <dt>
            <span id="datumpublicatie">Datumpublicatie</span>
          </dt>
          <dd>{besluitEntity.datumpublicatie}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{besluitEntity.datumstart}</dd>
          <dt>
            <span id="datumuiterlijkereactie">Datumuiterlijkereactie</span>
          </dt>
          <dd>{besluitEntity.datumuiterlijkereactie}</dd>
          <dt>
            <span id="datumverval">Datumverval</span>
          </dt>
          <dd>{besluitEntity.datumverval}</dd>
          <dt>
            <span id="datumverzending">Datumverzending</span>
          </dt>
          <dd>{besluitEntity.datumverzending}</dd>
          <dt>
            <span id="redenverval">Redenverval</span>
          </dt>
          <dd>{besluitEntity.redenverval}</dd>
          <dt>Isvastgelegdin Document</dt>
          <dd>{besluitEntity.isvastgelegdinDocument ? besluitEntity.isvastgelegdinDocument.id : ''}</dd>
          <dt>Isuitkomstvan Zaak</dt>
          <dd>{besluitEntity.isuitkomstvanZaak ? besluitEntity.isuitkomstvanZaak.id : ''}</dd>
          <dt>Isvan Besluittype</dt>
          <dd>{besluitEntity.isvanBesluittype ? besluitEntity.isvanBesluittype.id : ''}</dd>
          <dt>Kanvastgelegdzijnals Document</dt>
          <dd>
            {besluitEntity.kanvastgelegdzijnalsDocuments
              ? besluitEntity.kanvastgelegdzijnalsDocuments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {besluitEntity.kanvastgelegdzijnalsDocuments && i === besluitEntity.kanvastgelegdzijnalsDocuments.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/besluit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/besluit/${besluitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BesluitDetail;
