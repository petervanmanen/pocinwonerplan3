import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './onderwijsloopbaan.reducer';

export const OnderwijsloopbaanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const onderwijsloopbaanEntity = useAppSelector(state => state.onderwijsloopbaan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="onderwijsloopbaanDetailsHeading">Onderwijsloopbaan</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{onderwijsloopbaanEntity.id}</dd>
          <dt>Heeft Leerling</dt>
          <dd>{onderwijsloopbaanEntity.heeftLeerling ? onderwijsloopbaanEntity.heeftLeerling.id : ''}</dd>
          <dt>Kent School</dt>
          <dd>
            {onderwijsloopbaanEntity.kentSchools
              ? onderwijsloopbaanEntity.kentSchools.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {onderwijsloopbaanEntity.kentSchools && i === onderwijsloopbaanEntity.kentSchools.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/onderwijsloopbaan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/onderwijsloopbaan/${onderwijsloopbaanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OnderwijsloopbaanDetail;
