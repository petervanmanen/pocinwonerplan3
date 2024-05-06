import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './put.reducer';

export const PutDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const putEntity = useAppSelector(state => state.put.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="putDetailsHeading">Put</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{putEntity.id}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{putEntity.key}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{putEntity.projectcd}</dd>
          <dt>
            <span id="putnummer">Putnummer</span>
          </dt>
          <dd>{putEntity.putnummer}</dd>
          <dt>Heeftlocatie Locatie</dt>
          <dd>
            {putEntity.heeftlocatieLocaties
              ? putEntity.heeftlocatieLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {putEntity.heeftlocatieLocaties && i === putEntity.heeftlocatieLocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Project</dt>
          <dd>{putEntity.heeftProject ? putEntity.heeftProject.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/put" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/put/${putEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PutDetail;
