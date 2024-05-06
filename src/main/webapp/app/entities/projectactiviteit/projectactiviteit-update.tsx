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
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { getEntities as getVerzoeks } from 'app/entities/verzoek/verzoek.reducer';
import { IProjectactiviteit } from 'app/shared/model/projectactiviteit.model';
import { getEntity, updateEntity, createEntity, reset } from './projectactiviteit.reducer';

export const ProjectactiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projects = useAppSelector(state => state.project.entities);
  const verzoeks = useAppSelector(state => state.verzoek.entities);
  const projectactiviteitEntity = useAppSelector(state => state.projectactiviteit.entity);
  const loading = useAppSelector(state => state.projectactiviteit.loading);
  const updating = useAppSelector(state => state.projectactiviteit.updating);
  const updateSuccess = useAppSelector(state => state.projectactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/projectactiviteit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
      ...projectactiviteitEntity,
      ...values,
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
          ...projectactiviteitEntity,
          heeftProject: projectactiviteitEntity?.heeftProject?.id,
          betreftVerzoeks: projectactiviteitEntity?.betreftVerzoeks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.projectactiviteit.home.createOrEditLabel" data-cy="ProjectactiviteitCreateUpdateHeading">
            Create or edit a Projectactiviteit
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
                <ValidatedField name="id" required readOnly id="projectactiviteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                id="projectactiviteit-heeftProject"
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
                id="projectactiviteit-betreftVerzoek"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projectactiviteit" replace color="info">
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

export default ProjectactiviteitUpdate;
