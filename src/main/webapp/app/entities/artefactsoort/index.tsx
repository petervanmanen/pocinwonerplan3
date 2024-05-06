import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Artefactsoort from './artefactsoort';
import ArtefactsoortDetail from './artefactsoort-detail';
import ArtefactsoortUpdate from './artefactsoort-update';
import ArtefactsoortDeleteDialog from './artefactsoort-delete-dialog';

const ArtefactsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Artefactsoort />} />
    <Route path="new" element={<ArtefactsoortUpdate />} />
    <Route path=":id">
      <Route index element={<ArtefactsoortDetail />} />
      <Route path="edit" element={<ArtefactsoortUpdate />} />
      <Route path="delete" element={<ArtefactsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtefactsoortRoutes;
