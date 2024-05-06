import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './spoor.reducer';

export const SpoorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const spoorEntity = useAppSelector(state => state.spoor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="spoorDetailsHeading">Spoor</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{spoorEntity.id}</dd>
          <dt>
            <span id="aard">Aard</span>
          </dt>
          <dd>{spoorEntity.aard}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{spoorEntity.beschrijving}</dd>
          <dt>
            <span id="datering">Datering</span>
          </dt>
          <dd>{spoorEntity.datering}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{spoorEntity.datum ? <TextFormat value={spoorEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="hoogteboven">Hoogteboven</span>
          </dt>
          <dd>{spoorEntity.hoogteboven}</dd>
          <dt>
            <span id="hoogteonder">Hoogteonder</span>
          </dt>
          <dd>{spoorEntity.hoogteonder}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{spoorEntity.key}</dd>
          <dt>
            <span id="keyvlak">Keyvlak</span>
          </dt>
          <dd>{spoorEntity.keyvlak}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{spoorEntity.projectcd}</dd>
          <dt>
            <span id="putnummer">Putnummer</span>
          </dt>
          <dd>{spoorEntity.putnummer}</dd>
          <dt>
            <span id="spoornummer">Spoornummer</span>
          </dt>
          <dd>{spoorEntity.spoornummer}</dd>
          <dt>
            <span id="vlaknummer">Vlaknummer</span>
          </dt>
          <dd>{spoorEntity.vlaknummer}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{spoorEntity.vorm}</dd>
          <dt>Heeft Vlak</dt>
          <dd>{spoorEntity.heeftVlak ? spoorEntity.heeftVlak.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/spoor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/spoor/${spoorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpoorDetail;
