import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { getEntities as getInkooporders } from 'app/entities/inkooporder/inkooporder.reducer';
import { ITaakveld } from 'app/shared/model/taakveld.model';
import { getEntities as getTaakvelds } from 'app/entities/taakveld/taakveld.reducer';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { getEntities as getHoofdrekenings } from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntity, updateEntity, createEntity, reset } from './kostenplaats.reducer';

export const KostenplaatsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inkooporders = useAppSelector(state => state.inkooporder.entities);
  const taakvelds = useAppSelector(state => state.taakveld.entities);
  const hoofdrekenings = useAppSelector(state => state.hoofdrekening.entities);
  const projects = useAppSelector(state => state.project.entities);
  const kostenplaatsEntity = useAppSelector(state => state.kostenplaats.entity);
  const loading = useAppSelector(state => state.kostenplaats.loading);
  const updating = useAppSelector(state => state.kostenplaats.updating);
  const updateSuccess = useAppSelector(state => state.kostenplaats.updateSuccess);

  const handleClose = () => {
    navigate('/kostenplaats');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInkooporders({}));
    dispatch(getTaakvelds({}));
    dispatch(getHoofdrekenings({}));
    dispatch(getProjects({}));
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
      ...kostenplaatsEntity,
      ...values,
      heeftInkooporders: mapIdList(values.heeftInkooporders),
      heeftTaakvelds: mapIdList(values.heeftTaakvelds),
      heeftHoofdrekenings: mapIdList(values.heeftHoofdrekenings),
      heeftProjects: mapIdList(values.heeftProjects),
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
          ...kostenplaatsEntity,
          heeftInkooporders: kostenplaatsEntity?.heeftInkooporders?.map(e => e.id.toString()),
          heeftTaakvelds: kostenplaatsEntity?.heeftTaakvelds?.map(e => e.id.toString()),
          heeftHoofdrekenings: kostenplaatsEntity?.heeftHoofdrekenings?.map(e => e.id.toString()),
          heeftProjects: kostenplaatsEntity?.heeftProjects?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kostenplaats.home.createOrEditLabel" data-cy="KostenplaatsCreateUpdateHeading">
            Create or edit a Kostenplaats
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kostenplaats-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Btwcode" id="kostenplaats-btwcode" name="btwcode" data-cy="btwcode" type="text" />
              <ValidatedField
                label="Btwomschrijving"
                id="kostenplaats-btwomschrijving"
                name="btwomschrijving"
                data-cy="btwomschrijving"
                type="text"
              />
              <ValidatedField
                label="Kostenplaatssoortcode"
                id="kostenplaats-kostenplaatssoortcode"
                name="kostenplaatssoortcode"
                data-cy="kostenplaatssoortcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Kostenplaatssoortomschrijving"
                id="kostenplaats-kostenplaatssoortomschrijving"
                name="kostenplaatssoortomschrijving"
                data-cy="kostenplaatssoortomschrijving"
                type="text"
              />
              <ValidatedField
                label="Kostenplaatstypecode"
                id="kostenplaats-kostenplaatstypecode"
                name="kostenplaatstypecode"
                data-cy="kostenplaatstypecode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Kostenplaatstypeomschrijving"
                id="kostenplaats-kostenplaatstypeomschrijving"
                name="kostenplaatstypeomschrijving"
                data-cy="kostenplaatstypeomschrijving"
                type="text"
              />
              <ValidatedField label="Naam" id="kostenplaats-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="kostenplaats-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Heeft Inkooporder"
                id="kostenplaats-heeftInkooporder"
                data-cy="heeftInkooporder"
                type="select"
                multiple
                name="heeftInkooporders"
              >
                <option value="" key="0" />
                {inkooporders
                  ? inkooporders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Taakveld"
                id="kostenplaats-heeftTaakveld"
                data-cy="heeftTaakveld"
                type="select"
                multiple
                name="heeftTaakvelds"
              >
                <option value="" key="0" />
                {taakvelds
                  ? taakvelds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Hoofdrekening"
                id="kostenplaats-heeftHoofdrekening"
                data-cy="heeftHoofdrekening"
                type="select"
                multiple
                name="heeftHoofdrekenings"
              >
                <option value="" key="0" />
                {hoofdrekenings
                  ? hoofdrekenings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Project"
                id="kostenplaats-heeftProject"
                data-cy="heeftProject"
                type="select"
                multiple
                name="heeftProjects"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kostenplaats" replace color="info">
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

export default KostenplaatsUpdate;
