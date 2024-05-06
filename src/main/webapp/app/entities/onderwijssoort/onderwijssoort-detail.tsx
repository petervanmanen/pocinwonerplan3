import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './onderwijssoort.reducer';

export const OnderwijssoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const onderwijssoortEntity = useAppSelector(state => state.onderwijssoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="onderwijssoortDetailsHeading">Onderwijssoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{onderwijssoortEntity.id}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{onderwijssoortEntity.omschrijving}</dd>
          <dt>
            <span id="onderwijstype">Onderwijstype</span>
          </dt>
          <dd>{onderwijssoortEntity.onderwijstype}</dd>
          <dt>Heeft School</dt>
          <dd>
            {onderwijssoortEntity.heeftSchools
              ? onderwijssoortEntity.heeftSchools.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {onderwijssoortEntity.heeftSchools && i === onderwijssoortEntity.heeftSchools.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/onderwijssoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/onderwijssoort/${onderwijssoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OnderwijssoortDetail;
