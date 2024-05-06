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
import { IPut } from 'app/shared/model/put.model';
import { getEntity, updateEntity, createEntity, reset } from './put.reducer';

export const PutUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locaties = useAppSelector(state => state.locatie.entities);
  const projects = useAppSelector(state => state.project.entities);
  const putEntity = useAppSelector(state => state.put.entity);
  const loading = useAppSelector(state => state.put.loading);
  const updating = useAppSelector(state => state.put.updating);
  const updateSuccess = useAppSelector(state => state.put.updateSuccess);

  const handleClose = () => {
    navigate('/put');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocaties({}));
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
      ...putEntity,
      ...values,
      heeftlocatieLocaties: mapIdList(values.heeftlocatieLocaties),
      heeftProject: projects.find(it => it.id.toString() === values.heeftProject?.toString()),
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
          ...putEntity,
          heeftlocatieLocaties: putEntity?.heeftlocatieLocaties?.map(e => e.id.toString()),
          heeftProject: putEntity?.heeftProject?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.put.home.createOrEditLabel" data-cy="PutCreateUpdateHeading">
            Create or edit a Put
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="put-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Key" id="put-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Projectcd" id="put-projectcd" name="projectcd" data-cy="projectcd" type="text" />
              <ValidatedField label="Putnummer" id="put-putnummer" name="putnummer" data-cy="putnummer" type="text" />
              <ValidatedField
                label="Heeftlocatie Locatie"
                id="put-heeftlocatieLocatie"
                data-cy="heeftlocatieLocatie"
                type="select"
                multiple
                name="heeftlocatieLocaties"
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
              <ValidatedField id="put-heeftProject" name="heeftProject" data-cy="heeftProject" label="Heeft Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/put" replace color="info">
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

export default PutUpdate;
