import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './uitkeringsrun.reducer';

export const UitkeringsrunDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const uitkeringsrunEntity = useAppSelector(state => state.uitkeringsrun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="uitkeringsrunDetailsHeading">Uitkeringsrun</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{uitkeringsrunEntity.id}</dd>
          <dt>
            <span id="datumrun">Datumrun</span>
          </dt>
          <dd>
            {uitkeringsrunEntity.datumrun ? (
              <TextFormat value={uitkeringsrunEntity.datumrun} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="frequentie">Frequentie</span>
          </dt>
          <dd>{uitkeringsrunEntity.frequentie}</dd>
          <dt>
            <span id="perioderun">Perioderun</span>
          </dt>
          <dd>{uitkeringsrunEntity.perioderun}</dd>
          <dt>
            <span id="soortrun">Soortrun</span>
          </dt>
          <dd>{uitkeringsrunEntity.soortrun}</dd>
        </dl>
        <Button tag={Link} to="/uitkeringsrun" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/uitkeringsrun/${uitkeringsrunEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UitkeringsrunDetail;
