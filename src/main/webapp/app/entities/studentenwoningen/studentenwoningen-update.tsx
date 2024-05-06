import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudentenwoningen } from 'app/shared/model/studentenwoningen.model';
import { getEntity, updateEntity, createEntity, reset } from './studentenwoningen.reducer';

export const StudentenwoningenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const studentenwoningenEntity = useAppSelector(state => state.studentenwoningen.entity);
  const loading = useAppSelector(state => state.studentenwoningen.loading);
  const updating = useAppSelector(state => state.studentenwoningen.updating);
  const updateSuccess = useAppSelector(state => state.studentenwoningen.updateSuccess);

  const handleClose = () => {
    navigate('/studentenwoningen');
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
    if (values.huurprijs !== undefined && typeof values.huurprijs !== 'number') {
      values.huurprijs = Number(values.huurprijs);
    }

    const entity = {
      ...studentenwoningenEntity,
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
          ...studentenwoningenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.studentenwoningen.home.createOrEditLabel" data-cy="StudentenwoningenCreateUpdateHeading">
            Create or edit a Studentenwoningen
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
                <ValidatedField name="id" required readOnly id="studentenwoningen-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Huurprijs" id="studentenwoningen-huurprijs" name="huurprijs" data-cy="huurprijs" type="text" />
              <ValidatedField
                label="Zelfstandig"
                id="studentenwoningen-zelfstandig"
                name="zelfstandig"
                data-cy="zelfstandig"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/studentenwoningen" replace color="info">
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

export default StudentenwoningenUpdate;
