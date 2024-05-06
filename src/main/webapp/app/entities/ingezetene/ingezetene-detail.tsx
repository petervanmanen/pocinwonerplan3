import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ingezetene.reducer';

export const IngezeteneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ingezeteneEntity = useAppSelector(state => state.ingezetene.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ingezeteneDetailsHeading">Ingezetene</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ingezeteneEntity.id}</dd>
          <dt>
            <span id="aanduidingeuropeeskiesrecht">Aanduidingeuropeeskiesrecht</span>
          </dt>
          <dd>{ingezeteneEntity.aanduidingeuropeeskiesrecht ? 'true' : 'false'}</dd>
          <dt>
            <span id="aanduidinguitgeslotenkiesrecht">Aanduidinguitgeslotenkiesrecht</span>
          </dt>
          <dd>{ingezeteneEntity.aanduidinguitgeslotenkiesrecht ? 'true' : 'false'}</dd>
          <dt>
            <span id="datumverkrijgingverblijfstitel">Datumverkrijgingverblijfstitel</span>
          </dt>
          <dd>{ingezeteneEntity.datumverkrijgingverblijfstitel}</dd>
          <dt>
            <span id="datumverliesverblijfstitel">Datumverliesverblijfstitel</span>
          </dt>
          <dd>{ingezeteneEntity.datumverliesverblijfstitel}</dd>
          <dt>
            <span id="indicatieblokkering">Indicatieblokkering</span>
          </dt>
          <dd>{ingezeteneEntity.indicatieblokkering}</dd>
          <dt>
            <span id="indicatiecurateleregister">Indicatiecurateleregister</span>
          </dt>
          <dd>{ingezeteneEntity.indicatiecurateleregister}</dd>
          <dt>
            <span id="indicatiegezagminderjarige">Indicatiegezagminderjarige</span>
          </dt>
          <dd>{ingezeteneEntity.indicatiegezagminderjarige}</dd>
          <dt>Heeft Verblijfstitel</dt>
          <dd>{ingezeteneEntity.heeftVerblijfstitel ? ingezeteneEntity.heeftVerblijfstitel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ingezetene" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ingezetene/${ingezeteneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IngezeteneDetail;
