import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './linkbaarcmdbitem.reducer';

export const LinkbaarcmdbitemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const linkbaarcmdbitemEntity = useAppSelector(state => state.linkbaarcmdbitem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="linkbaarcmdbitemDetailsHeading">Linkbaarcmdbitem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{linkbaarcmdbitemEntity.id}</dd>
        </dl>
        <Button tag={Link} to="/linkbaarcmdbitem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/linkbaarcmdbitem/${linkbaarcmdbitemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LinkbaarcmdbitemDetail;
