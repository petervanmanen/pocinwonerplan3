import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './onderwijsinstituut.reducer';

export const OnderwijsinstituutDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const onderwijsinstituutEntity = useAppSelector(state => state.onderwijsinstituut.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="onderwijsinstituutDetailsHeading">Onderwijsinstituut</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{onderwijsinstituutEntity.id}</dd>
          <dt>Wordtgegevendoor Opleiding</dt>
          <dd>
            {onderwijsinstituutEntity.wordtgegevendoorOpleidings
              ? onderwijsinstituutEntity.wordtgegevendoorOpleidings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {onderwijsinstituutEntity.wordtgegevendoorOpleidings &&
                    i === onderwijsinstituutEntity.wordtgegevendoorOpleidings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/onderwijsinstituut" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/onderwijsinstituut/${onderwijsinstituutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OnderwijsinstituutDetail;
