import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './klantbeoordeling.reducer';

export const KlantbeoordelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const klantbeoordelingEntity = useAppSelector(state => state.klantbeoordeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="klantbeoordelingDetailsHeading">Klantbeoordeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{klantbeoordelingEntity.id}</dd>
          <dt>
            <span id="beoordeling">Beoordeling</span>
          </dt>
          <dd>{klantbeoordelingEntity.beoordeling}</dd>
          <dt>
            <span id="categorie">Categorie</span>
          </dt>
          <dd>{klantbeoordelingEntity.categorie}</dd>
          <dt>
            <span id="contactopnemen">Contactopnemen</span>
          </dt>
          <dd>{klantbeoordelingEntity.contactopnemen ? 'true' : 'false'}</dd>
          <dt>
            <span id="ddbeoordeling">Ddbeoordeling</span>
          </dt>
          <dd>
            {klantbeoordelingEntity.ddbeoordeling ? (
              <TextFormat value={klantbeoordelingEntity.ddbeoordeling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="kanaal">Kanaal</span>
          </dt>
          <dd>{klantbeoordelingEntity.kanaal}</dd>
          <dt>
            <span id="onderwerp">Onderwerp</span>
          </dt>
          <dd>{klantbeoordelingEntity.onderwerp}</dd>
          <dt>
            <span id="subcategorie">Subcategorie</span>
          </dt>
          <dd>{klantbeoordelingEntity.subcategorie}</dd>
          <dt>Doet Betrokkene</dt>
          <dd>{klantbeoordelingEntity.doetBetrokkene ? klantbeoordelingEntity.doetBetrokkene.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/klantbeoordeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/klantbeoordeling/${klantbeoordelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KlantbeoordelingDetail;
