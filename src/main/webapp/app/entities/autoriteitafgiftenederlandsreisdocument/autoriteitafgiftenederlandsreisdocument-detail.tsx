import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './autoriteitafgiftenederlandsreisdocument.reducer';

export const AutoriteitafgiftenederlandsreisdocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const autoriteitafgiftenederlandsreisdocumentEntity = useAppSelector(state => state.autoriteitafgiftenederlandsreisdocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="autoriteitafgiftenederlandsreisdocumentDetailsHeading">Autoriteitafgiftenederlandsreisdocument</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{autoriteitafgiftenederlandsreisdocumentEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{autoriteitafgiftenederlandsreisdocumentEntity.code}</dd>
          <dt>
            <span id="datumbegingeldigheidautoriteitvanafgifte">Datumbegingeldigheidautoriteitvanafgifte</span>
          </dt>
          <dd>
            {autoriteitafgiftenederlandsreisdocumentEntity.datumbegingeldigheidautoriteitvanafgifte ? (
              <TextFormat
                value={autoriteitafgiftenederlandsreisdocumentEntity.datumbegingeldigheidautoriteitvanafgifte}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidautoriteitvanafgifte">Datumeindegeldigheidautoriteitvanafgifte</span>
          </dt>
          <dd>
            {autoriteitafgiftenederlandsreisdocumentEntity.datumeindegeldigheidautoriteitvanafgifte ? (
              <TextFormat
                value={autoriteitafgiftenederlandsreisdocumentEntity.datumeindegeldigheidautoriteitvanafgifte}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{autoriteitafgiftenederlandsreisdocumentEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/autoriteitafgiftenederlandsreisdocument" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/autoriteitafgiftenederlandsreisdocument/${autoriteitafgiftenederlandsreisdocumentEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AutoriteitafgiftenederlandsreisdocumentDetail;
