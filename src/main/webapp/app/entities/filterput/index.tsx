import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Filterput from './filterput';
import FilterputDetail from './filterput-detail';
import FilterputUpdate from './filterput-update';
import FilterputDeleteDialog from './filterput-delete-dialog';

const FilterputRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Filterput />} />
    <Route path="new" element={<FilterputUpdate />} />
    <Route path=":id">
      <Route index element={<FilterputDetail />} />
      <Route path="edit" element={<FilterputUpdate />} />
      <Route path="delete" element={<FilterputDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FilterputRoutes;
