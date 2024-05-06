import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInburgeringstraject } from 'app/shared/model/inburgeringstraject.model';
import { getEntities as getInburgeringstrajects } from 'app/entities/inburgeringstraject/inburgeringstraject.reducer';
import { IExamen } from 'app/shared/model/examen.model';
import { getEntity, updateEntity, createEntity, reset } from './examen.reducer';

export const ExamenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inburgeringstrajects = useAppSelector(state => state.inburgeringstraject.entities);
  const examenEntity = useAppSelector(state => state.examen.entity);
  const loading = useAppSelector(state => state.examen.loading);
  const updating = useAppSelector(state => state.examen.updating);
  const updateSuccess = useAppSelector(state => state.examen.updateSuccess);

  const handleClose = () => {
    navigate('/examen');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInburgeringstrajects({}));
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
      ...examenEntity,
      ...values,
      afgerondmetInburgeringstraject: inburgeringstrajects.find(
        it => it.id.toString() === values.afgerondmetInburgeringstraject?.toString(),
      ),
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
          ...examenEntity,
          afgerondmetInburgeringstraject: examenEntity?.afgerondmetInburgeringstraject?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.examen.home.createOrEditLabel" data-cy="ExamenCreateUpdateHeading">
            Create or edit a Examen
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="examen-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                id="examen-afgerondmetInburgeringstraject"
                name="afgerondmetInburgeringstraject"
                data-cy="afgerondmetInburgeringstraject"
                label="Afgerondmet Inburgeringstraject"
                type="select"
              >
                <option value="" key="0" />
                {inburgeringstrajects
                  ? inburgeringstrajects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/examen" replace color="info">
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

export default ExamenUpdate;
