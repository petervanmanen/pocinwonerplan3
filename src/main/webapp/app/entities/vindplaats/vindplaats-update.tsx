import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IDepot } from 'app/shared/model/depot.model';
import { getEntities as getDepots } from 'app/entities/depot/depot.reducer';
import { IKast } from 'app/shared/model/kast.model';
import { getEntities as getKasts } from 'app/entities/kast/kast.reducer';
import { IPlank } from 'app/shared/model/plank.model';
import { getEntities as getPlanks } from 'app/entities/plank/plank.reducer';
import { IStelling } from 'app/shared/model/stelling.model';
import { getEntities as getStellings } from 'app/entities/stelling/stelling.reducer';
import { IVindplaats } from 'app/shared/model/vindplaats.model';
import { getEntity, updateEntity, createEntity, reset } from './vindplaats.reducer';

export const VindplaatsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projects = useAppSelector(state => state.project.entities);
  const depots = useAppSelector(state => state.depot.entities);
  const kasts = useAppSelector(state => state.kast.entities);
  const planks = useAppSelector(state => state.plank.entities);
  const stellings = useAppSelector(state => state.stelling.entities);
  const vindplaatsEntity = useAppSelector(state => state.vindplaats.entity);
  const loading = useAppSelector(state => state.vindplaats.loading);
  const updating = useAppSelector(state => state.vindplaats.updating);
  const updateSuccess = useAppSelector(state => state.vindplaats.updateSuccess);

  const handleClose = () => {
    navigate('/vindplaats');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjects({}));
    dispatch(getDepots({}));
    dispatch(getKasts({}));
    dispatch(getPlanks({}));
    dispatch(getStellings({}));
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
      ...vindplaatsEntity,
      ...values,
      hoortbijProject: projects.find(it => it.id.toString() === values.hoortbijProject?.toString()),
      istevindeninDepot: depots.find(it => it.id.toString() === values.istevindeninDepot?.toString()),
      istevindeninKast: kasts.find(it => it.id.toString() === values.istevindeninKast?.toString()),
      istevindeninPlank: planks.find(it => it.id.toString() === values.istevindeninPlank?.toString()),
      istevindeninStelling: stellings.find(it => it.id.toString() === values.istevindeninStelling?.toString()),
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
          ...vindplaatsEntity,
          hoortbijProject: vindplaatsEntity?.hoortbijProject?.id,
          istevindeninDepot: vindplaatsEntity?.istevindeninDepot?.id,
          istevindeninKast: vindplaatsEntity?.istevindeninKast?.id,
          istevindeninPlank: vindplaatsEntity?.istevindeninPlank?.id,
          istevindeninStelling: vindplaatsEntity?.istevindeninStelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vindplaats.home.createOrEditLabel" data-cy="VindplaatsCreateUpdateHeading">
            Create or edit a Vindplaats
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vindplaats-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aard"
                id="vindplaats-aard"
                name="aard"
                data-cy="aard"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Begindatering"
                id="vindplaats-begindatering"
                name="begindatering"
                data-cy="begindatering"
                type="text"
              />
              <ValidatedField label="Beschrijving" id="vindplaats-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField label="Bibliografie" id="vindplaats-bibliografie" name="bibliografie" data-cy="bibliografie" type="text" />
              <ValidatedField label="Datering" id="vindplaats-datering" name="datering" data-cy="datering" type="text" />
              <ValidatedField label="Depot" id="vindplaats-depot" name="depot" data-cy="depot" type="text" />
              <ValidatedField label="Documentatie" id="vindplaats-documentatie" name="documentatie" data-cy="documentatie" type="text" />
              <ValidatedField label="Einddatering" id="vindplaats-einddatering" name="einddatering" data-cy="einddatering" type="text" />
              <ValidatedField label="Gemeente" id="vindplaats-gemeente" name="gemeente" data-cy="gemeente" type="text" />
              <ValidatedField label="Locatie" id="vindplaats-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Mobilia" id="vindplaats-mobilia" name="mobilia" data-cy="mobilia" type="text" />
              <ValidatedField label="Onderzoek" id="vindplaats-onderzoek" name="onderzoek" data-cy="onderzoek" type="text" />
              <ValidatedField
                label="Projectcode"
                id="vindplaats-projectcode"
                name="projectcode"
                data-cy="projectcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Vindplaats" id="vindplaats-vindplaats" name="vindplaats" data-cy="vindplaats" type="text" />
              <ValidatedField
                id="vindplaats-hoortbijProject"
                name="hoortbijProject"
                data-cy="hoortbijProject"
                label="Hoortbij Project"
                type="select"
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
              <ValidatedField
                id="vindplaats-istevindeninDepot"
                name="istevindeninDepot"
                data-cy="istevindeninDepot"
                label="Istevindenin Depot"
                type="select"
              >
                <option value="" key="0" />
                {depots
                  ? depots.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="vindplaats-istevindeninKast"
                name="istevindeninKast"
                data-cy="istevindeninKast"
                label="Istevindenin Kast"
                type="select"
              >
                <option value="" key="0" />
                {kasts
                  ? kasts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="vindplaats-istevindeninPlank"
                name="istevindeninPlank"
                data-cy="istevindeninPlank"
                label="Istevindenin Plank"
                type="select"
              >
                <option value="" key="0" />
                {planks
                  ? planks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="vindplaats-istevindeninStelling"
                name="istevindeninStelling"
                data-cy="istevindeninStelling"
                label="Istevindenin Stelling"
                type="select"
              >
                <option value="" key="0" />
                {stellings
                  ? stellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vindplaats" replace color="info">
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

export default VindplaatsUpdate;
