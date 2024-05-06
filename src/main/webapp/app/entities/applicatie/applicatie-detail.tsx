import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './applicatie.reducer';

export const ApplicatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const applicatieEntity = useAppSelector(state => state.applicatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicatieDetailsHeading">Applicatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicatieEntity.id}</dd>
          <dt>
            <span id="applicatieurl">Applicatieurl</span>
          </dt>
          <dd>{applicatieEntity.applicatieurl}</dd>
          <dt>
            <span id="beheerstatus">Beheerstatus</span>
          </dt>
          <dd>{applicatieEntity.beheerstatus}</dd>
          <dt>
            <span id="beleidsdomein">Beleidsdomein</span>
          </dt>
          <dd>{applicatieEntity.beleidsdomein}</dd>
          <dt>
            <span id="categorie">Categorie</span>
          </dt>
          <dd>{applicatieEntity.categorie}</dd>
          <dt>
            <span id="guid">Guid</span>
          </dt>
          <dd>{applicatieEntity.guid}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{applicatieEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{applicatieEntity.omschrijving}</dd>
          <dt>
            <span id="packagingstatus">Packagingstatus</span>
          </dt>
          <dd>{applicatieEntity.packagingstatus}</dd>
          <dt>Heeftleverancier Leverancier</dt>
          <dd>{applicatieEntity.heeftleverancierLeverancier ? applicatieEntity.heeftleverancierLeverancier.id : ''}</dd>
          <dt>Heeftdocumenten Document</dt>
          <dd>
            {applicatieEntity.heeftdocumentenDocuments
              ? applicatieEntity.heeftdocumentenDocuments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicatieEntity.heeftdocumentenDocuments && i === applicatieEntity.heeftdocumentenDocuments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Rollen Medewerker</dt>
          <dd>
            {applicatieEntity.rollenMedewerkers
              ? applicatieEntity.rollenMedewerkers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicatieEntity.rollenMedewerkers && i === applicatieEntity.rollenMedewerkers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/applicatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicatie/${applicatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicatieDetail;
