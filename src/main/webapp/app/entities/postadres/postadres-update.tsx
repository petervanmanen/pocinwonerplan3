import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWoonplaats } from 'app/shared/model/woonplaats.model';
import { getEntities as getWoonplaats } from 'app/entities/woonplaats/woonplaats.reducer';
import { IPostadres } from 'app/shared/model/postadres.model';
import { getEntity, updateEntity, createEntity, reset } from './postadres.reducer';

export const PostadresUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const woonplaats = useAppSelector(state => state.woonplaats.entities);
  const postadresEntity = useAppSelector(state => state.postadres.entity);
  const loading = useAppSelector(state => state.postadres.loading);
  const updating = useAppSelector(state => state.postadres.updating);
  const updateSuccess = useAppSelector(state => state.postadres.updateSuccess);

  const handleClose = () => {
    navigate('/postadres');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWoonplaats({}));
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
      ...postadresEntity,
      ...values,
      heeftalscorrespondentieadrespostadresinWoonplaats: woonplaats.find(
        it => it.id.toString() === values.heeftalscorrespondentieadrespostadresinWoonplaats?.toString(),
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
          ...postadresEntity,
          heeftalscorrespondentieadrespostadresinWoonplaats: postadresEntity?.heeftalscorrespondentieadrespostadresinWoonplaats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.postadres.home.createOrEditLabel" data-cy="PostadresCreateUpdateHeading">
            Create or edit a Postadres
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="postadres-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Postadrestype" id="postadres-postadrestype" name="postadrestype" data-cy="postadrestype" type="text" />
              <ValidatedField
                label="Postbusofantwoordnummer"
                id="postadres-postbusofantwoordnummer"
                name="postbusofantwoordnummer"
                data-cy="postbusofantwoordnummer"
                type="text"
              />
              <ValidatedField
                label="Postcodepostadres"
                id="postadres-postcodepostadres"
                name="postcodepostadres"
                data-cy="postcodepostadres"
                type="text"
              />
              <ValidatedField
                id="postadres-heeftalscorrespondentieadrespostadresinWoonplaats"
                name="heeftalscorrespondentieadrespostadresinWoonplaats"
                data-cy="heeftalscorrespondentieadrespostadresinWoonplaats"
                label="Heeftalscorrespondentieadrespostadresin Woonplaats"
                type="select"
              >
                <option value="" key="0" />
                {woonplaats
                  ? woonplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/postadres" replace color="info">
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

export default PostadresUpdate;
