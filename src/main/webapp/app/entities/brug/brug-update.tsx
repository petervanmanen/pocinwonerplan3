import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBrug } from 'app/shared/model/brug.model';
import { getEntity, updateEntity, createEntity, reset } from './brug.reducer';

export const BrugUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const brugEntity = useAppSelector(state => state.brug.entity);
  const loading = useAppSelector(state => state.brug.loading);
  const updating = useAppSelector(state => state.brug.updating);
  const updateSuccess = useAppSelector(state => state.brug.updateSuccess);

  const handleClose = () => {
    navigate('/brug');
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
      ...brugEntity,
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
          ...brugEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.brug.home.createOrEditLabel" data-cy="BrugCreateUpdateHeading">
            Create or edit a Brug
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="brug-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantaloverspanningen"
                id="brug-aantaloverspanningen"
                name="aantaloverspanningen"
                data-cy="aantaloverspanningen"
                type="text"
              />
              <ValidatedField label="Bedienaar" id="brug-bedienaar" name="bedienaar" data-cy="bedienaar" type="text" />
              <ValidatedField
                label="Bedieningstijden"
                id="brug-bedieningstijden"
                name="bedieningstijden"
                data-cy="bedieningstijden"
                type="text"
              />
              <ValidatedField
                label="Belastingklassenieuw"
                id="brug-belastingklassenieuw"
                name="belastingklassenieuw"
                data-cy="belastingklassenieuw"
                type="text"
              />
              <ValidatedField
                label="Belastingklasseoud"
                id="brug-belastingklasseoud"
                name="belastingklasseoud"
                data-cy="belastingklasseoud"
                type="text"
              />
              <ValidatedField label="Beweegbaar" id="brug-beweegbaar" name="beweegbaar" data-cy="beweegbaar" check type="checkbox" />
              <ValidatedField label="Doorrijbreedte" id="brug-doorrijbreedte" name="doorrijbreedte" data-cy="doorrijbreedte" type="text" />
              <ValidatedField label="Draagvermogen" id="brug-draagvermogen" name="draagvermogen" data-cy="draagvermogen" type="text" />
              <ValidatedField label="Hoofdroute" id="brug-hoofdroute" name="hoofdroute" data-cy="hoofdroute" type="text" />
              <ValidatedField label="Hoofdvaarroute" id="brug-hoofdvaarroute" name="hoofdvaarroute" data-cy="hoofdvaarroute" type="text" />
              <ValidatedField
                label="Maximaaltoelaatbaarvoertuiggewicht"
                id="brug-maximaaltoelaatbaarvoertuiggewicht"
                name="maximaaltoelaatbaarvoertuiggewicht"
                data-cy="maximaaltoelaatbaarvoertuiggewicht"
                type="text"
              />
              <ValidatedField
                label="Maximaleasbelasting"
                id="brug-maximaleasbelasting"
                name="maximaleasbelasting"
                data-cy="maximaleasbelasting"
                type="text"
              />
              <ValidatedField
                label="Maximaleoverspanning"
                id="brug-maximaleoverspanning"
                name="maximaleoverspanning"
                data-cy="maximaleoverspanning"
                type="text"
              />
              <ValidatedField label="Statischmoment" id="brug-statischmoment" name="statischmoment" data-cy="statischmoment" type="text" />
              <ValidatedField label="Type" id="brug-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="brug-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Zwaarstevoertuig"
                id="brug-zwaarstevoertuig"
                name="zwaarstevoertuig"
                data-cy="zwaarstevoertuig"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/brug" replace color="info">
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

export default BrugUpdate;
