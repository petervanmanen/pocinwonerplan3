import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeerling } from 'app/shared/model/leerling.model';
import { getEntities as getLeerlings } from 'app/entities/leerling/leerling.reducer';
import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { IOnderwijsloopbaan } from 'app/shared/model/onderwijsloopbaan.model';
import { getEntity, updateEntity, createEntity, reset } from './onderwijsloopbaan.reducer';

export const OnderwijsloopbaanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leerlings = useAppSelector(state => state.leerling.entities);
  const schools = useAppSelector(state => state.school.entities);
  const onderwijsloopbaanEntity = useAppSelector(state => state.onderwijsloopbaan.entity);
  const loading = useAppSelector(state => state.onderwijsloopbaan.loading);
  const updating = useAppSelector(state => state.onderwijsloopbaan.updating);
  const updateSuccess = useAppSelector(state => state.onderwijsloopbaan.updateSuccess);

  const handleClose = () => {
    navigate('/onderwijsloopbaan');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeerlings({}));
    dispatch(getSchools({}));
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
      ...onderwijsloopbaanEntity,
      ...values,
      heeftLeerling: leerlings.find(it => it.id.toString() === values.heeftLeerling?.toString()),
      kentSchools: mapIdList(values.kentSchools),
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
          ...onderwijsloopbaanEntity,
          heeftLeerling: onderwijsloopbaanEntity?.heeftLeerling?.id,
          kentSchools: onderwijsloopbaanEntity?.kentSchools?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.onderwijsloopbaan.home.createOrEditLabel" data-cy="OnderwijsloopbaanCreateUpdateHeading">
            Create or edit a Onderwijsloopbaan
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
                <ValidatedField name="id" required readOnly id="onderwijsloopbaan-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                id="onderwijsloopbaan-heeftLeerling"
                name="heeftLeerling"
                data-cy="heeftLeerling"
                label="Heeft Leerling"
                type="select"
              >
                <option value="" key="0" />
                {leerlings
                  ? leerlings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Kent School"
                id="onderwijsloopbaan-kentSchool"
                data-cy="kentSchool"
                type="select"
                multiple
                name="kentSchools"
              >
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/onderwijsloopbaan" replace color="info">
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

export default OnderwijsloopbaanUpdate;
