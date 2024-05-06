import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './score.reducer';

export const ScoreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scoreEntity = useAppSelector(state => state.score.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="scoreDetailsHeading">Score</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{scoreEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{scoreEntity.datum}</dd>
          <dt>Scorebijleeggebied Leefgebied</dt>
          <dd>{scoreEntity.scorebijleeggebiedLeefgebied ? scoreEntity.scorebijleeggebiedLeefgebied.id : ''}</dd>
          <dt>Hoogtescore Scoresoort</dt>
          <dd>{scoreEntity.hoogtescoreScoresoort ? scoreEntity.hoogtescoreScoresoort.id : ''}</dd>
          <dt>Heeft Client</dt>
          <dd>{scoreEntity.heeftClient ? scoreEntity.heeftClient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/score" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/score/${scoreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScoreDetail;
