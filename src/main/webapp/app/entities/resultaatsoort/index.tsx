import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Resultaatsoort from './resultaatsoort';
import ResultaatsoortDetail from './resultaatsoort-detail';
import ResultaatsoortUpdate from './resultaatsoort-update';
import ResultaatsoortDeleteDialog from './resultaatsoort-delete-dialog';

const ResultaatsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Resultaatsoort />} />
    <Route path="new" element={<ResultaatsoortUpdate />} />
    <Route path=":id">
      <Route index element={<ResultaatsoortDetail />} />
      <Route path="edit" element={<ResultaatsoortUpdate />} />
      <Route path="delete" element={<ResultaatsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ResultaatsoortRoutes;
