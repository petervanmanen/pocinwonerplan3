import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pas.reducer';

export const PasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pasEntity = useAppSelector(state => state.pas.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pasDetailsHeading">Pas</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pasEntity.id}</dd>
          <dt>
            <span id="adresaanduiding">Adresaanduiding</span>
          </dt>
          <dd>{pasEntity.adresaanduiding}</dd>
          <dt>
            <span id="pasnummer">Pasnummer</span>
          </dt>
          <dd>{pasEntity.pasnummer}</dd>
          <dt>Geldigvoor Milieustraat</dt>
          <dd>
            {pasEntity.geldigvoorMilieustraats
              ? pasEntity.geldigvoorMilieustraats.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {pasEntity.geldigvoorMilieustraats && i === pasEntity.geldigvoorMilieustraats.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pas" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pas/${pasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PasDetail;
