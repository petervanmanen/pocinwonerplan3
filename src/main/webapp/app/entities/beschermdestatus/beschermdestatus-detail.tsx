import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beschermdestatus.reducer';

export const BeschermdestatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beschermdestatusEntity = useAppSelector(state => state.beschermdestatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beschermdestatusDetailsHeading">Beschermdestatus</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beschermdestatusEntity.id}</dd>
          <dt>
            <span id="bronnen">Bronnen</span>
          </dt>
          <dd>{beschermdestatusEntity.bronnen}</dd>
          <dt>
            <span id="complex">Complex</span>
          </dt>
          <dd>{beschermdestatusEntity.complex}</dd>
          <dt>
            <span id="datuminschrijvingregister">Datuminschrijvingregister</span>
          </dt>
          <dd>
            {beschermdestatusEntity.datuminschrijvingregister ? (
              <TextFormat value={beschermdestatusEntity.datuminschrijvingregister} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gemeentelijkmonumentcode">Gemeentelijkmonumentcode</span>
          </dt>
          <dd>{beschermdestatusEntity.gemeentelijkmonumentcode}</dd>
          <dt>
            <span id="gezichtscode">Gezichtscode</span>
          </dt>
          <dd>{beschermdestatusEntity.gezichtscode}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{beschermdestatusEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{beschermdestatusEntity.omschrijving}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{beschermdestatusEntity.opmerkingen}</dd>
          <dt>
            <span id="rijksmonumentcode">Rijksmonumentcode</span>
          </dt>
          <dd>{beschermdestatusEntity.rijksmonumentcode}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{beschermdestatusEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/beschermdestatus" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beschermdestatus/${beschermdestatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeschermdestatusDetail;
