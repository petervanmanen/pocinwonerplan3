import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.reducer';

export const SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity = useAppSelector(
    state => state.sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.entity,
  );
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sluitingofaangaanhuwelijkofgeregistreerdpartnerschapDetailsHeading">
          Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.id}</dd>
          <dt>
            <span id="buitenlandseplaatsaanvang">Buitenlandseplaatsaanvang</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.buitenlandseplaatsaanvang}</dd>
          <dt>
            <span id="buitenlandseregioaanvang">Buitenlandseregioaanvang</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.buitenlandseregioaanvang}</dd>
          <dt>
            <span id="datumaanvang">Datumaanvang</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.datumaanvang}</dd>
          <dt>
            <span id="gemeenteaanvang">Gemeenteaanvang</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.gemeenteaanvang}</dd>
          <dt>
            <span id="landofgebiedaanvang">Landofgebiedaanvang</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.landofgebiedaanvang}</dd>
          <dt>
            <span id="omschrijvinglocatieaanvang">Omschrijvinglocatieaanvang</span>
          </dt>
          <dd>{sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.omschrijvinglocatieaanvang}</dd>
        </dl>
        <Button
          tag={Link}
          to="/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap"
          replace
          color="info"
          data-cy="entityDetailsBackButton"
        >
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/${sluitingofaangaanhuwelijkofgeregistreerdpartnerschapEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SluitingofaangaanhuwelijkofgeregistreerdpartnerschapDetail;
