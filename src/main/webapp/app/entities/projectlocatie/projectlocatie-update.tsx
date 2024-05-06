import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { getEntities as getVerzoeks } from 'app/entities/verzoek/verzoek.reducer';
import { IProjectlocatie } from 'app/shared/model/projectlocatie.model';
import { getEntity, updateEntity, createEntity, reset } from './projectlocatie.reducer';

export const ProjectlocatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locaties = useAppSelector(state => state.locatie.entities);
  const projects = useAppSelector(state => state.project.entities);
  const verzoeks = useAppSelector(state => state.verzoek.entities);
  const projectlocatieEntity = useAppSelector(state => state.projectlocatie.entity);
  const loading = useAppSelector(state => state.projectlocatie.loading);
  const updating = useAppSelector(state => state.projectlocatie.updating);
  const updateSuccess = useAppSelector(state => state.projectlocatie.updateSuccess);

  const handleClose = () => {
    navigate('/projectlocatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocaties({}));
    dispatch(getProjects({}));
    dispatch(getVerzoeks({}));
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
      ...projectlocatieEntity,
      ...values,
      betreftLocatie: locaties.find(it => it.id.toString() === values.betreftLocatie?.toString()),
      heeftProject: projects.find(it => it.id.toString() === values.heeftProject?.toString()),
      betreftVerzoeks: mapIdList(values.betreftVerzoeks),
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
          ...projectlocatieEntity,
          betreftLocatie: projectlocatieEntity?.betreftLocatie?.id,
          heeftProject: projectlocatieEntity?.heeftProject?.id,
          betreftVerzoeks: projectlocatieEntity?.betreftVerzoeks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.projectlocatie.home.createOrEditLabel" data-cy="ProjectlocatieCreateUpdateHeading">
            Create or edit a Projectlocatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="projectlocatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Adres" id="projectlocatie-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField
                label="Kadastraalperceel"
                id="projectlocatie-kadastraalperceel"
                name="kadastraalperceel"
                data-cy="kadastraalperceel"
                type="text"
              />
              <ValidatedField
                label="Kadastralegemeente"
                id="projectlocatie-kadastralegemeente"
                name="kadastralegemeente"
                data-cy="kadastralegemeente"
                type="text"
              />
              <ValidatedField
                label="Kadastralesectie"
                id="projectlocatie-kadastralesectie"
                name="kadastralesectie"
                data-cy="kadastralesectie"
                type="text"
              />
              <ValidatedField
                id="projectlocatie-betreftLocatie"
                name="betreftLocatie"
                data-cy="betreftLocatie"
                label="Betreft Locatie"
                type="select"
              >
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="projectlocatie-heeftProject"
                name="heeftProject"
                data-cy="heeftProject"
                label="Heeft Project"
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
                label="Betreft Verzoek"
                id="projectlocatie-betreftVerzoek"
                data-cy="betreftVerzoek"
                type="select"
                multiple
                name="betreftVerzoeks"
              >
                <option value="" key="0" />
                {verzoeks
                  ? verzoeks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projectlocatie" replace color="info">
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

export default ProjectlocatieUpdate;
