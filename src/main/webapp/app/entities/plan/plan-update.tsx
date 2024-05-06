import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProgramma } from 'app/shared/model/programma.model';
import { getEntities as getProgrammas } from 'app/entities/programma/programma.reducer';
import { IProjectleider } from 'app/shared/model/projectleider.model';
import { getEntities as getProjectleiders } from 'app/entities/projectleider/projectleider.reducer';
import { IProjectontwikkelaar } from 'app/shared/model/projectontwikkelaar.model';
import { getEntities as getProjectontwikkelaars } from 'app/entities/projectontwikkelaar/projectontwikkelaar.reducer';
import { IPlan } from 'app/shared/model/plan.model';
import { getEntity, updateEntity, createEntity, reset } from './plan.reducer';

export const PlanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const programmas = useAppSelector(state => state.programma.entities);
  const projectleiders = useAppSelector(state => state.projectleider.entities);
  const projectontwikkelaars = useAppSelector(state => state.projectontwikkelaar.entities);
  const planEntity = useAppSelector(state => state.plan.entity);
  const loading = useAppSelector(state => state.plan.loading);
  const updating = useAppSelector(state => state.plan.updating);
  const updateSuccess = useAppSelector(state => state.plan.updateSuccess);

  const handleClose = () => {
    navigate('/plan');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProgrammas({}));
    dispatch(getProjectleiders({}));
    dispatch(getProjectontwikkelaars({}));
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
      ...planEntity,
      ...values,
      binnenprogrammaProgramma: programmas.find(it => it.id.toString() === values.binnenprogrammaProgramma?.toString()),
      isprojectleidervanProjectleider: projectleiders.find(it => it.id.toString() === values.isprojectleidervanProjectleider?.toString()),
      heeftProjectontwikkelaars: mapIdList(values.heeftProjectontwikkelaars),
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
          ...planEntity,
          binnenprogrammaProgramma: planEntity?.binnenprogrammaProgramma?.id,
          isprojectleidervanProjectleider: planEntity?.isprojectleidervanProjectleider?.id,
          heeftProjectontwikkelaars: planEntity?.heeftProjectontwikkelaars?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.plan.home.createOrEditLabel" data-cy="PlanCreateUpdateHeading">
            Create or edit a Plan
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="plan-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Zeventigprocentverkocht"
                id="plan-zeventigprocentverkocht"
                name="zeventigprocentverkocht"
                data-cy="zeventigprocentverkocht"
                check
                type="checkbox"
              />
              <ValidatedField label="Aardgasloos" id="plan-aardgasloos" name="aardgasloos" data-cy="aardgasloos" check type="checkbox" />
              <ValidatedField
                label="Bestemminggoedgekeurd"
                id="plan-bestemminggoedgekeurd"
                name="bestemminggoedgekeurd"
                data-cy="bestemminggoedgekeurd"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Eersteoplevering"
                id="plan-eersteoplevering"
                name="eersteoplevering"
                data-cy="eersteoplevering"
                type="date"
              />
              <ValidatedField
                label="Eigendomgemeente"
                id="plan-eigendomgemeente"
                name="eigendomgemeente"
                data-cy="eigendomgemeente"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Gebiedstransformatie"
                id="plan-gebiedstransformatie"
                name="gebiedstransformatie"
                data-cy="gebiedstransformatie"
                check
                type="checkbox"
              />
              <ValidatedField label="Intentie" id="plan-intentie" name="intentie" data-cy="intentie" check type="checkbox" />
              <ValidatedField
                label="Laatsteoplevering"
                id="plan-laatsteoplevering"
                name="laatsteoplevering"
                data-cy="laatsteoplevering"
                type="date"
              />
              <ValidatedField label="Naam" id="plan-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Nummer" id="plan-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField
                label="Onherroepelijk"
                id="plan-onherroepelijk"
                name="onherroepelijk"
                data-cy="onherroepelijk"
                check
                type="checkbox"
              />
              <ValidatedField label="Percelen" id="plan-percelen" name="percelen" data-cy="percelen" type="text" />
              <ValidatedField label="Startbouw" id="plan-startbouw" name="startbouw" data-cy="startbouw" type="date" />
              <ValidatedField label="Startverkoop" id="plan-startverkoop" name="startverkoop" data-cy="startverkoop" type="date" />
              <ValidatedField
                id="plan-binnenprogrammaProgramma"
                name="binnenprogrammaProgramma"
                data-cy="binnenprogrammaProgramma"
                label="Binnenprogramma Programma"
                type="select"
              >
                <option value="" key="0" />
                {programmas
                  ? programmas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="plan-isprojectleidervanProjectleider"
                name="isprojectleidervanProjectleider"
                data-cy="isprojectleidervanProjectleider"
                label="Isprojectleidervan Projectleider"
                type="select"
              >
                <option value="" key="0" />
                {projectleiders
                  ? projectleiders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Projectontwikkelaar"
                id="plan-heeftProjectontwikkelaar"
                data-cy="heeftProjectontwikkelaar"
                type="select"
                multiple
                name="heeftProjectontwikkelaars"
              >
                <option value="" key="0" />
                {projectontwikkelaars
                  ? projectontwikkelaars.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plan" replace color="info">
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

export default PlanUpdate;
