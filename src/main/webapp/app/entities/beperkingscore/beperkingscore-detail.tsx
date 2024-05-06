import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beperkingscore.reducer';

export const BeperkingscoreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beperkingscoreEntity = useAppSelector(state => state.beperkingscore.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beperkingscoreDetailsHeading">Beperkingscore</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beperkingscoreEntity.id}</dd>
          <dt>
            <span id="commentaar">Commentaar</span>
          </dt>
          <dd>{beperkingscoreEntity.commentaar}</dd>
          <dt>
            <span id="score">Score</span>
          </dt>
          <dd>{beperkingscoreEntity.score}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{beperkingscoreEntity.wet}</dd>
          <dt>Iseen Beperkingscoresoort</dt>
          <dd>{beperkingscoreEntity.iseenBeperkingscoresoort ? beperkingscoreEntity.iseenBeperkingscoresoort.id : ''}</dd>
          <dt>Empty Beperking</dt>
          <dd>{beperkingscoreEntity.emptyBeperking ? beperkingscoreEntity.emptyBeperking.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beperkingscore" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beperkingscore/${beperkingscoreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeperkingscoreDetail;
