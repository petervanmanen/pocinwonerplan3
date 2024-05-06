import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './selectietabelaanbesteding.reducer';

export const SelectietabelaanbestedingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const selectietabelaanbestedingEntity = useAppSelector(state => state.selectietabelaanbesteding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="selectietabelaanbestedingDetailsHeading">Selectietabelaanbesteding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{selectietabelaanbestedingEntity.id}</dd>
          <dt>
            <span id="aanbestedingsoort">Aanbestedingsoort</span>
          </dt>
          <dd>{selectietabelaanbestedingEntity.aanbestedingsoort}</dd>
          <dt>
            <span id="drempelbedragtot">Drempelbedragtot</span>
          </dt>
          <dd>{selectietabelaanbestedingEntity.drempelbedragtot}</dd>
          <dt>
            <span id="drempelbedragvanaf">Drempelbedragvanaf</span>
          </dt>
          <dd>{selectietabelaanbestedingEntity.drempelbedragvanaf}</dd>
          <dt>
            <span id="opdrachtcategorie">Opdrachtcategorie</span>
          </dt>
          <dd>{selectietabelaanbestedingEntity.opdrachtcategorie}</dd>
          <dt>
            <span id="openbaar">Openbaar</span>
          </dt>
          <dd>{selectietabelaanbestedingEntity.openbaar}</dd>
        </dl>
        <Button tag={Link} to="/selectietabelaanbesteding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/selectietabelaanbesteding/${selectietabelaanbestedingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SelectietabelaanbestedingDetail;
