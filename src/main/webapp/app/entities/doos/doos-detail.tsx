import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doos.reducer';

export const DoosDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doosEntity = useAppSelector(state => state.doos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doosDetailsHeading">Doos</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{doosEntity.id}</dd>
          <dt>
            <span id="doosnummer">Doosnummer</span>
          </dt>
          <dd>{doosEntity.doosnummer}</dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{doosEntity.herkomst}</dd>
          <dt>
            <span id="inhoud">Inhoud</span>
          </dt>
          <dd>{doosEntity.inhoud}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{doosEntity.key}</dd>
          <dt>
            <span id="keymagazijnlocatie">Keymagazijnlocatie</span>
          </dt>
          <dd>{doosEntity.keymagazijnlocatie}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{doosEntity.projectcd}</dd>
          <dt>Staatop Magazijnlocatie</dt>
          <dd>{doosEntity.staatopMagazijnlocatie ? doosEntity.staatopMagazijnlocatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doos/${doosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoosDetail;
