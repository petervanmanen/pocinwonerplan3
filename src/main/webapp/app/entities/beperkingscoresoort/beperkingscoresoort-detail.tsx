import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beperkingscoresoort.reducer';

export const BeperkingscoresoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beperkingscoresoortEntity = useAppSelector(state => state.beperkingscoresoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beperkingscoresoortDetailsHeading">Beperkingscoresoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beperkingscoresoortEntity.id}</dd>
          <dt>
            <span id="vraag">Vraag</span>
          </dt>
          <dd>{beperkingscoresoortEntity.vraag}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{beperkingscoresoortEntity.wet}</dd>
        </dl>
        <Button tag={Link} to="/beperkingscoresoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beperkingscoresoort/${beperkingscoresoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeperkingscoresoortDetail;
