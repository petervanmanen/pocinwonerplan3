import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './postadres.reducer';

export const PostadresDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postadresEntity = useAppSelector(state => state.postadres.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postadresDetailsHeading">Postadres</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{postadresEntity.id}</dd>
          <dt>
            <span id="postadrestype">Postadrestype</span>
          </dt>
          <dd>{postadresEntity.postadrestype}</dd>
          <dt>
            <span id="postbusofantwoordnummer">Postbusofantwoordnummer</span>
          </dt>
          <dd>{postadresEntity.postbusofantwoordnummer}</dd>
          <dt>
            <span id="postcodepostadres">Postcodepostadres</span>
          </dt>
          <dd>{postadresEntity.postcodepostadres}</dd>
          <dt>Heeftalscorrespondentieadrespostadresin Woonplaats</dt>
          <dd>
            {postadresEntity.heeftalscorrespondentieadrespostadresinWoonplaats
              ? postadresEntity.heeftalscorrespondentieadrespostadresinWoonplaats.id
              : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/postadres" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/postadres/${postadresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostadresDetail;
