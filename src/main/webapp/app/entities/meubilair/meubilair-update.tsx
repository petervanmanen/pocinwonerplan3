import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMeubilair } from 'app/shared/model/meubilair.model';
import { getEntity, updateEntity, createEntity, reset } from './meubilair.reducer';

export const MeubilairUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const meubilairEntity = useAppSelector(state => state.meubilair.entity);
  const loading = useAppSelector(state => state.meubilair.loading);
  const updating = useAppSelector(state => state.meubilair.updating);
  const updateSuccess = useAppSelector(state => state.meubilair.updateSuccess);

  const handleClose = () => {
    navigate('/meubilair');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...meubilairEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...meubilairEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.meubilair.home.createOrEditLabel" data-cy="MeubilairCreateUpdateHeading">
            Create or edit a Meubilair
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="meubilair-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanleghoogte" id="meubilair-aanleghoogte" name="aanleghoogte" data-cy="aanleghoogte" type="text" />
              <ValidatedField label="Bouwjaar" id="meubilair-bouwjaar" name="bouwjaar" data-cy="bouwjaar" type="text" />
              <ValidatedField label="Breedte" id="meubilair-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Datumaanschaf" id="meubilair-datumaanschaf" name="datumaanschaf" data-cy="datumaanschaf" type="date" />
              <ValidatedField label="Diameter" id="meubilair-diameter" name="diameter" data-cy="diameter" type="text" />
              <ValidatedField label="Fabrikant" id="meubilair-fabrikant" name="fabrikant" data-cy="fabrikant" type="text" />
              <ValidatedField label="Gewicht" id="meubilair-gewicht" name="gewicht" data-cy="gewicht" type="text" />
              <ValidatedField label="Hoogte" id="meubilair-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField label="Installateur" id="meubilair-installateur" name="installateur" data-cy="installateur" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="meubilair-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Jaarpraktischeinde"
                id="meubilair-jaarpraktischeinde"
                name="jaarpraktischeinde"
                data-cy="jaarpraktischeinde"
                type="text"
              />
              <ValidatedField label="Kleur" id="meubilair-kleur" name="kleur" data-cy="kleur" type="text" />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="meubilair-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="meubilair-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="meubilair-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="meubilair-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField
                label="Meubilairmateriaal"
                id="meubilair-meubilairmateriaal"
                name="meubilairmateriaal"
                data-cy="meubilairmateriaal"
                type="text"
              />
              <ValidatedField label="Model" id="meubilair-model" name="model" data-cy="model" type="text" />
              <ValidatedField label="Ondergrond" id="meubilair-ondergrond" name="ondergrond" data-cy="ondergrond" type="text" />
              <ValidatedField label="Oppervlakte" id="meubilair-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Prijsaanschaf" id="meubilair-prijsaanschaf" name="prijsaanschaf" data-cy="prijsaanschaf" type="text" />
              <ValidatedField label="Serienummer" id="meubilair-serienummer" name="serienummer" data-cy="serienummer" type="text" />
              <ValidatedField label="Transponder" id="meubilair-transponder" name="transponder" data-cy="transponder" type="text" />
              <ValidatedField
                label="Transponderlocatie"
                id="meubilair-transponderlocatie"
                name="transponderlocatie"
                data-cy="transponderlocatie"
                type="text"
              />
              <ValidatedField label="Typefundering" id="meubilair-typefundering" name="typefundering" data-cy="typefundering" type="text" />
              <ValidatedField label="Typeplaat" id="meubilair-typeplaat" name="typeplaat" data-cy="typeplaat" check type="checkbox" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/meubilair" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MeubilairUpdate;
