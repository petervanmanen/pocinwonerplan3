import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { IOnderwijssoort } from 'app/shared/model/onderwijssoort.model';
import { getEntity, updateEntity, createEntity, reset } from './onderwijssoort.reducer';

export const OnderwijssoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schools = useAppSelector(state => state.school.entities);
  const onderwijssoortEntity = useAppSelector(state => state.onderwijssoort.entity);
  const loading = useAppSelector(state => state.onderwijssoort.loading);
  const updating = useAppSelector(state => state.onderwijssoort.updating);
  const updateSuccess = useAppSelector(state => state.onderwijssoort.updateSuccess);

  const handleClose = () => {
    navigate('/onderwijssoort');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
      ...onderwijssoortEntity,
      ...values,
      heeftSchools: mapIdList(values.heeftSchools),
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
          ...onderwijssoortEntity,
          heeftSchools: onderwijssoortEntity?.heeftSchools?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.onderwijssoort.home.createOrEditLabel" data-cy="OnderwijssoortCreateUpdateHeading">
            Create or edit a Onderwijssoort
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
                <ValidatedField name="id" required readOnly id="onderwijssoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Omschrijving"
                id="onderwijssoort-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Onderwijstype"
                id="onderwijssoort-onderwijstype"
                name="onderwijstype"
                data-cy="onderwijstype"
                type="text"
              />
              <ValidatedField
                label="Heeft School"
                id="onderwijssoort-heeftSchool"
                data-cy="heeftSchool"
                type="select"
                multiple
                name="heeftSchools"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/onderwijssoort" replace color="info">
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

export default OnderwijssoortUpdate;
