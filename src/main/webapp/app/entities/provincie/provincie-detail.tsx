import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './provincie.reducer';

export const ProvincieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const provincieEntity = useAppSelector(state => state.provincie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="provincieDetailsHeading">Provincie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{provincieEntity.id}</dd>
          <dt>
            <span id="datumeindeprovincie">Datumeindeprovincie</span>
          </dt>
          <dd>
            {provincieEntity.datumeindeprovincie ? (
              <TextFormat value={provincieEntity.datumeindeprovincie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingangprovincie">Datumingangprovincie</span>
          </dt>
          <dd>
            {provincieEntity.datumingangprovincie ? (
              <TextFormat value={provincieEntity.datumingangprovincie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hoofdstad">Hoofdstad</span>
          </dt>
          <dd>{provincieEntity.hoofdstad}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{provincieEntity.oppervlakte}</dd>
          <dt>
            <span id="oppervlakteland">Oppervlakteland</span>
          </dt>
          <dd>{provincieEntity.oppervlakteland}</dd>
          <dt>
            <span id="provinciecode">Provinciecode</span>
          </dt>
          <dd>{provincieEntity.provinciecode}</dd>
          <dt>
            <span id="provincienaam">Provincienaam</span>
          </dt>
          <dd>{provincieEntity.provincienaam}</dd>
        </dl>
        <Button tag={Link} to="/provincie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/provincie/${provincieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProvincieDetail;
