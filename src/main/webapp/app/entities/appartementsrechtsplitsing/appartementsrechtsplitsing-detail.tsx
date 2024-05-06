import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './appartementsrechtsplitsing.reducer';

export const AppartementsrechtsplitsingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appartementsrechtsplitsingEntity = useAppSelector(state => state.appartementsrechtsplitsing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appartementsrechtsplitsingDetailsHeading">Appartementsrechtsplitsing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appartementsrechtsplitsingEntity.id}</dd>
          <dt>
            <span id="ddentificatieappartementsrechtsplitsing">Ddentificatieappartementsrechtsplitsing</span>
          </dt>
          <dd>{appartementsrechtsplitsingEntity.ddentificatieappartementsrechtsplitsing}</dd>
          <dt>
            <span id="typesplitsing">Typesplitsing</span>
          </dt>
          <dd>{appartementsrechtsplitsingEntity.typesplitsing}</dd>
        </dl>
        <Button tag={Link} to="/appartementsrechtsplitsing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/appartementsrechtsplitsing/${appartementsrechtsplitsingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppartementsrechtsplitsingDetail;
