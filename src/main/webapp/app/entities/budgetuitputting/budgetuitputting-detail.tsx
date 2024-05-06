import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './budgetuitputting.reducer';

export const BudgetuitputtingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const budgetuitputtingEntity = useAppSelector(state => state.budgetuitputting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="budgetuitputtingDetailsHeading">Budgetuitputting</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{budgetuitputtingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {budgetuitputtingEntity.datum ? (
              <TextFormat value={budgetuitputtingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="uitgenutbedrag">Uitgenutbedrag</span>
          </dt>
          <dd>{budgetuitputtingEntity.uitgenutbedrag}</dd>
        </dl>
        <Button tag={Link} to="/budgetuitputting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/budgetuitputting/${budgetuitputtingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BudgetuitputtingDetail;
