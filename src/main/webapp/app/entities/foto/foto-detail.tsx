import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './foto.reducer';

export const FotoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fotoEntity = useAppSelector(state => state.foto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fotoDetailsHeading">Foto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fotoEntity.id}</dd>
          <dt>
            <span id="bestandsgrootte">Bestandsgrootte</span>
          </dt>
          <dd>{fotoEntity.bestandsgrootte}</dd>
          <dt>
            <span id="bestandsnaam">Bestandsnaam</span>
          </dt>
          <dd>{fotoEntity.bestandsnaam}</dd>
          <dt>
            <span id="bestandstype">Bestandstype</span>
          </dt>
          <dd>{fotoEntity.bestandstype}</dd>
          <dt>
            <span id="datumtijd">Datumtijd</span>
          </dt>
          <dd>{fotoEntity.datumtijd}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{fotoEntity.locatie}</dd>
          <dt>
            <span id="pixelsx">Pixelsx</span>
          </dt>
          <dd>{fotoEntity.pixelsx}</dd>
          <dt>
            <span id="pixelsy">Pixelsy</span>
          </dt>
          <dd>{fotoEntity.pixelsy}</dd>
        </dl>
        <Button tag={Link} to="/foto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/foto/${fotoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FotoDetail;
